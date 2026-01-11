package com.valuable.valuable._one.domain.dto;

import java.math.BigDecimal;

public class LoanReleaseRequest {
    private String loanNumber;
    private BigDecimal amount;
    private Long accountId;

    public LoanReleaseRequest(String loanNumber, BigDecimal amount, Long accountId) {
        this.loanNumber = loanNumber;
        this.amount = amount;
        this.accountId = accountId;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getAccountId() {
        return accountId;
    }
}

