package com.valuable.valuable._one.service;

import com.valuable.valuable._one.domain.dto.HomeLoanRequest;
import com.valuable.valuable._one.domain.dto.HomeLoanResponse;
import com.valuable.valuable._one.domain.dto.LoanReleaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HomeLoanService {

    void releaseLoan(LoanReleaseRequest request);

    void updateLoanStatus(String loanReference, String status);

    void updateRequestedAmount(String loanReference, Double requestedAmount);

    Page<HomeLoanResponse> searchLoans(String reference, Pageable pageable);

    HomeLoanResponse createHomeLoan(HomeLoanRequest request);
}

