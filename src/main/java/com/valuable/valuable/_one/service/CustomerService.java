package com.valuable.valuable._one.service;

import com.valuable.valuable._one.domain.dto.CustomerRequest;
import com.valuable.valuable._one.domain.dto.CustomerResponse;
import com.valuable.valuable._one.domain.entity.CustomerEntity;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);

    CustomerEntity getCustomer(String nic);
}
