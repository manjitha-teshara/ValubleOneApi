package com.valuable.valuable._one.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_nic", columnList = "nic")
})
public class CustomerEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String nic;

    @Column(nullable = false)
    private String fullName;

    // customer can have multiple loans
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeLoanEntity> loans = new ArrayList<>();

    protected CustomerEntity() {}

    public CustomerEntity(String nic, String fullName) {
        this.nic = nic;
        this.fullName = fullName;
    }

    public Long getId() { return id; }
    public String getNic() { return nic; }
    public String getFullName() { return fullName; }
    public List<HomeLoanEntity> getLoans() { return loans; }

    // method to add loan
    public void addLoan(HomeLoanEntity loan) {
        loans.add(loan);
        loan.setCustomer(this);
    }

    // method to remove loan
    public void removeLoan(HomeLoanEntity loan) {
        loans.remove(loan);
        loan.setCustomer(null);
    }
}

