package com.valuable.valuable._one.service.impl;

import com.valuable.valuable._one.domain.dto.HomeLoanRequest;
import com.valuable.valuable._one.domain.dto.HomeLoanResponse;
import com.valuable.valuable._one.domain.dto.LoanReleaseRequest;
import com.valuable.valuable._one.domain.entity.CustomerEntity;
import com.valuable.valuable._one.domain.entity.HomeLoanEntity;
import com.valuable.valuable._one.domain.enums.LoanStatus;
import com.valuable.valuable._one.exception.ExternalServiceException;
import com.valuable.valuable._one.exception.ResourceNotFoundException;
import com.valuable.valuable._one.mapper.HomeLoanMapper;
import com.valuable.valuable._one.repository.HomeLoanRepository;
import com.valuable.valuable._one.service.CustomerService;
import com.valuable.valuable._one.service.HomeLoanService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class HomeLoanServiceImpl implements HomeLoanService {

    private static final Logger log =
            LoggerFactory.getLogger(HomeLoanServiceImpl.class);

    private final HomeLoanRepository loanRepository;
    private final WebClient webClient;
    private final HomeLoanMapper mapper;

    private final CustomerService customerService;

    public HomeLoanServiceImpl(HomeLoanRepository loanRepository, WebClient webClient, HomeLoanMapper mapper, CustomerService customerService) {
        this.loanRepository = loanRepository;
        this.webClient = webClient;
        this.mapper = mapper;
        this.customerService = customerService;
    }

    @Override
    public Page<HomeLoanResponse> searchLoans(String reference, Pageable pageable) {
        return loanRepository
                .findByLoanReferenceContainingIgnoreCase(reference, pageable)
                .map(loan -> new HomeLoanResponse(
                        loan.getLoanReference(),
                        loan.getApprovedAmount(),
                        loan.getCustomer().getFullName(),
                        loan.getStatus()
                ));
    }

    @Override
    public HomeLoanResponse createHomeLoan(HomeLoanRequest request) {
        CustomerEntity customer = customerService.getCustomer(request.getCustomerNic());
        HomeLoanEntity entity = mapper.toEntity(request, customer);
        return mapper.toResponse(loanRepository.save(entity));
    }


    @Override
    @Transactional
    @Retry(name = "releaseLoanRetry", fallbackMethod = "releaseLoanFallback")
    @CircuitBreaker(name = "releaseLoanCircuitBreaker", fallbackMethod = "releaseLoanFallback")
    public void releaseLoan(LoanReleaseRequest request) throws ExternalServiceException{

        log.info("Starting loan release. loanRef={}", request.getLoanNumber());

        HomeLoanEntity loan = loanRepository
                .findByLoanReference(request.getLoanNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        log.info("Calling bank transfer API. loanRef={}", request.getLoanNumber());
        // external bank transfer request
        webClient.post()
                .uri("https://bank-api.bank.com/transfer")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();

        // update loan status
        loan.setStatus(LoanStatus.DISBURSED);
        loanRepository.save(loan);

        log.info("Loan released successfully. loanRef={}", request.getLoanNumber());
    }

    public void releaseLoanFallback(LoanReleaseRequest request, Throwable t) {

        log.error(
                "Loan release failed. loanRef={} reason={}",
                request.getLoanNumber(), t.getMessage(), t);

        //  publish event to Kafka

        //still not able to success
        throw new ExternalServiceException(
                "Bank transfer failed. Please try again later."
        );
    }

    @Override
    @Transactional
    public void updateLoanStatus(String loanReference, String status) {
        HomeLoanEntity loan = loanRepository
                .findByLoanReference(loanReference)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        loan.setStatus(LoanStatus.valueOf(status.toUpperCase()));
        loanRepository.save(loan);
    }


    @Override
    @Transactional
    public void updateRequestedAmount(String loanReference, Double requestedAmount) {
        HomeLoanEntity loan = loanRepository
                .findByLoanReference(loanReference)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        loan.setRequestedAmount(BigDecimal.valueOf(requestedAmount));
        loan.setStatus(LoanStatus.PENDING); // automatically set to PENDING when update request amount
        loanRepository.save(loan);
    }

}