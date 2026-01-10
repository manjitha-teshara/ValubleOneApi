package com.valuable.valuable._one.mapper;

import com.valuable.valuable._one.domain.dto.CustomerRequest;
import com.valuable.valuable._one.domain.dto.CustomerResponse;
import com.valuable.valuable._one.domain.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {


    public CustomerEntity toEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        }

        return new CustomerEntity(
                request.getNic(),
                request.getFullName()
        );
    }

    public CustomerResponse toResponse(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        return new CustomerResponse(
                entity.getId(),
                entity.getNic(),
                entity.getFullName()
        );
    }
}

