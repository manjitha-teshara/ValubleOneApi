package com.valuable.valuable._one.service.impl;

import com.valuable.valuable._one.domain.dto.CustomerRequest;
import com.valuable.valuable._one.domain.dto.CustomerResponse;
import com.valuable.valuable._one.domain.entity.CustomerEntity;
import com.valuable.valuable._one.exception.ResourceNotFoundException;
import com.valuable.valuable._one.mapper.CustomerMapper;
import com.valuable.valuable._one.repository.CustomerRepository;
import com.valuable.valuable._one.service.CustomerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;


    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        if (repository.existsByNic(request.getNic())) {
            throw new IllegalArgumentException("customer already exists for NIC: " + request.getNic());
        }

        CustomerEntity customer = mapper.toEntity(request);

        CustomerEntity saved = repository.save(customer);

        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "customers", key = "#nic")
    public CustomerEntity getCustomer(String nic) {
        return repository.findByNic(nic)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found for NIC: " + nic));
    }
}
