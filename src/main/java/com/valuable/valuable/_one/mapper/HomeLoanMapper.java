package com.valuable.valuable._one.mapper;

import com.valuable.valuable._one.domain.dto.HomeLoanRequest;
import com.valuable.valuable._one.domain.dto.HomeLoanResponse;
import com.valuable.valuable._one.domain.entity.CustomerEntity;
import com.valuable.valuable._one.domain.entity.HomeLoanEntity;
import com.valuable.valuable._one.domain.enums.LoanStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HomeLoanMapper {

    public HomeLoanResponse toResponse(HomeLoanEntity entity) {
        if (entity == null) {
            return null;
        }

        return new HomeLoanResponse(
                entity.getLoanReference(),
                entity.getApprovedAmount(),
                entity.getCustomer() != null ? entity.getCustomer().getFullName() : "",
                entity.getStatus()
        );
    }

    public HomeLoanEntity toEntity(HomeLoanRequest request, CustomerEntity customer) {
        if (request == null) {
            return null;
        }

        return new HomeLoanEntity(request.getLoanReference(), request.getRequestedAmount(), customer);
    }
}
