package com.valuable.valuable._one.repository;

import com.valuable.valuable._one.domain.entity.CustomerEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByNic(@NotBlank @Size(max = 12) String nic);


    Optional<CustomerEntity> findByNic(String nic);
}
