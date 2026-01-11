package com.valuable.valuable._one.service.impl;

import com.valuable.valuable._one.domain.dto.CustomerRequest;
import com.valuable.valuable._one.domain.dto.CustomerResponse;
import com.valuable.valuable._one.domain.entity.CustomerEntity;
import com.valuable.valuable._one.exception.ResourceNotFoundException;
import com.valuable.valuable._one.mapper.CustomerMapper;
import com.valuable.valuable._one.repository.CustomerRepository;
import com.valuable.valuable._one.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository repository;

    private final CustomerMapper mapper;


    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        log.info("Creating customer. nic={}", request.getNic());

        if (repository.existsByNic(request.getNic())) {
            throw new IllegalArgumentException("customer already exists for NIC: " + request.getNic());
        }

        CustomerEntity customer = mapper.toEntity(request);

        CustomerEntity saved = repository.save(customer);

        log.info("Customer created successfully. nic={}", saved.getNic());

        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "customers", key = "#nic")
    public CustomerEntity getCustomer(String nic) {

        log.info("Fetching customer. nic={}", nic);

        return repository.findByNic(nic)
                .orElseThrow(() -> {
                    log.warn("Customer not found. nic={}", nic);
                    return new ResourceNotFoundException(
                            "Customer not found for NIC: " + nic
                    );
                });
    }
}
