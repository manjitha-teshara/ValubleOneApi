package com.valuable.valuable._one.domain.dto;

import java.math.BigDecimal;

public class HomeLoanRequest {

        private String loanReference;

        private String customerNic;

        private BigDecimal requestedAmount;

    public HomeLoanRequest(String loanReference, String customerNic, BigDecimal requestedAmount) {
        this.loanReference = loanReference;
        this.customerNic = customerNic;
        this.requestedAmount = requestedAmount;
    }

    public String getLoanReference() {
        return loanReference;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
}
