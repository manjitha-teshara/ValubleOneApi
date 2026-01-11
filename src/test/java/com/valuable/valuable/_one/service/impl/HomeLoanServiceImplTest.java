package com.valuable.valuable._one.service.impl;


import com.valuable.valuable._one.domain.dto.HomeLoanRequest;
import com.valuable.valuable._one.domain.dto.LoanReleaseRequest;
import com.valuable.valuable._one.domain.entity.CustomerEntity;
import com.valuable.valuable._one.domain.entity.HomeLoanEntity;
import com.valuable.valuable._one.domain.enums.LoanStatus;
import com.valuable.valuable._one.exception.ResourceNotFoundException;
import com.valuable.valuable._one.mapper.HomeLoanMapper;
import com.valuable.valuable._one.repository.HomeLoanRepository;
import com.valuable.valuable._one.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class HomeLoanServiceImplTest {

    @Mock
    private HomeLoanRepository loanRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private HomeLoanMapper mapper;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private HomeLoanServiceImpl service;

    private CustomerEntity customer;
    private HomeLoanEntity loan;

    @BeforeEach
    void setup() {
        customer = new CustomerEntity("123456789V","John Doe");

        loan = new HomeLoanEntity("HL-001", new BigDecimal(120000.00), customer);
    }

    @Test
    void createHomeLoan_success() {
        HomeLoanRequest request = new HomeLoanRequest("HL-001", "123456789V", new BigDecimal("120000"));

        when(customerService.getCustomer("123456789V")).thenReturn(customer);
        when(mapper.toEntity(eq(request), eq(customer))).thenReturn(loan);
        when(loanRepository.save(loan)).thenReturn(loan);
        when(mapper.toResponse(loan)).thenReturn(null); // response mapping tested elsewhere

        assertDoesNotThrow(() -> service.createHomeLoan(request));

        verify(customerService).getCustomer("123456789V");
        verify(loanRepository).save(loan);
    }


    @Test
    void releaseLoan_success() {
        LoanReleaseRequest request =
                new LoanReleaseRequest("HL-001", new BigDecimal("120000"), 777888L);

        when(loanRepository.findByLoanReference("HL-001"))
                .thenReturn(Optional.of(loan));

        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(uriSpec);
        when(uriSpec.bodyValue(any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity())
                .thenReturn(Mono.empty());

        service.releaseLoan(request);

        assertEquals(LoanStatus.DISBURSED, loan.getStatus());
        verify(loanRepository).save(loan);
    }


    @Test
    void releaseLoan_loanNotFound() {
        LoanReleaseRequest request =
                new LoanReleaseRequest("INVALID_NO", new BigDecimal(1200), 777888L);

        when(loanRepository.findByLoanReference("INVALID_NO"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.releaseLoan(request));
    }



    @Test
    void updateLoanStatus_success() {
        when(loanRepository.findByLoanReference("HL-001"))
                .thenReturn(Optional.of(loan));

        service.updateLoanStatus("HL-001", "disbursed");

        assertEquals(LoanStatus.DISBURSED, loan.getStatus());
        verify(loanRepository).save(loan);
    }


    @Test
    void updateRequestedAmount_success() {
        when(loanRepository.findByLoanReference("HL-001"))
                .thenReturn(Optional.of(loan));

        service.updateRequestedAmount("HL-001", 500000.00);

        assertEquals(BigDecimal.valueOf(500000.00), loan.getRequestedAmount());
        assertEquals(LoanStatus.PENDING, loan.getStatus());
        verify(loanRepository).save(loan);
    }
}
