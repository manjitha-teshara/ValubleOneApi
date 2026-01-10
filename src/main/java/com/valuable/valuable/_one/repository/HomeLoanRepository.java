package com.valuable.valuable._one.repository;

import com.valuable.valuable._one.domain.entity.HomeLoanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeLoanRepository extends JpaRepository<HomeLoanEntity, Long> {

    @EntityGraph(attributePaths = "customer")
    Page<HomeLoanEntity> findByLoanReferenceContainingIgnoreCase(String reference, Pageable pageable);

    Optional<HomeLoanEntity> findByLoanReference(String loanReference);
}

