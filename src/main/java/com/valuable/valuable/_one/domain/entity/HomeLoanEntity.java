package com.valuable.valuable._one.domain.entity;

import com.valuable.valuable._one.domain.enums.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_loans", indexes = {
        @Index(name = "idx_loan_reference", columnList = "loanReference"),
        @Index(name = "idx_loan_status", columnList = "status")
})
public class HomeLoanEntity extends MutableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loanReference;

    @Column(nullable = false)
    private BigDecimal requestedAmount;

    @Column(nullable = true)
    private BigDecimal approvedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(nullable = true)
    private LocalDateTime approvedAt;

    protected HomeLoanEntity() {}

    public HomeLoanEntity(String loanReference,
                          BigDecimal requestedAmount,
                          CustomerEntity customer) {
        this.loanReference = loanReference;
        this.requestedAmount = requestedAmount;
        this.customer = customer;
        this.status = LoanStatus.PENDING;
        this.approvedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getLoanReference() {
        return loanReference;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public HomeLoanEntity setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
        return this;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }
}

