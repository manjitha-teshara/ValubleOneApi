package com.valuable.valuable._one.domain.dto;

import java.math.BigDecimal;

public class HomeLoanRequest {

        private String loanReference;

        private String customerNic;

        private BigDecimal requestedAmount;

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
