package com.valuable.valuable._one.domain.dto;

import com.valuable.valuable._one.domain.enums.LoanStatus;

import java.math.BigDecimal;

public class HomeLoanResponse {

    private final String loanReference;
    private final BigDecimal approvedAmount;
    private final String customerName;
    private final LoanStatus status;

    public HomeLoanResponse(String loanReference,
                            BigDecimal approvedAmount,
                            String customerName,
                            LoanStatus status) {
        this.loanReference = loanReference;
        this.approvedAmount = approvedAmount;
        this.customerName = customerName;
        this.status = status;
    }

    public String getLoanReference() { return loanReference; }
    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public String getCustomerName() { return customerName; }
    public LoanStatus getStatus() { return status; }
}

