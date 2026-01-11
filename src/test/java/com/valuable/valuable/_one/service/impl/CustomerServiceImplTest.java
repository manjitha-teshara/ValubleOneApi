package com.valuable.valuable._one.service.impl;

import com.valuable.valuable._one.domain.dto.CustomerRequest;
import com.valuable.valuable._one.domain.dto.CustomerResponse;
import com.valuable.valuable._one.domain.entity.CustomerEntity;
import com.valuable.valuable._one.exception.ResourceNotFoundException;
import com.valuable.valuable._one.mapper.CustomerMapper;
import com.valuable.valuable._one.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private CustomerRepository repository;
    private CustomerMapper mapper;
    private CustomerServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerRepository.class);
        mapper = mock(CustomerMapper.class);
        service = new CustomerServiceImpl(repository, mapper);
    }

    @Test
    void createCustomer_shouldCreateSuccessfully() {
        // given
        CustomerRequest request = new CustomerRequest();
        request.setNic("991234567V");
        request.setFullName("Manjitha Teshara");

        CustomerEntity entity = new CustomerEntity(request.getNic(), request.getFullName());

        CustomerEntity savedEntity = new CustomerEntity(request.getNic(), request.getFullName());

        CustomerResponse response = new CustomerResponse(1L, request.getNic(), request.getFullName());

        when(repository.existsByNic(request.getNic())).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toResponse(savedEntity)).thenReturn(response);

        CustomerResponse result = service.createCustomer(request);

        assertNotNull(result);
        assertEquals("991234567V", result.getNic());
        assertEquals("Manjitha Teshara", result.getFullName());

        verify(repository).existsByNic(request.getNic());
        verify(repository).save(entity);
        verify(mapper).toEntity(request);
        verify(mapper).toResponse(savedEntity);
    }

    @Test
    void createCustomer_shouldThrowException_whenNicExists() {
        CustomerRequest request = new CustomerRequest();
        request.setNic("991234567V");
        request.setFullName("John Doe");

        when(repository.existsByNic(request.getNic())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createCustomer(request));

        assertEquals("customer already exists for NIC: 991234567V", ex.getMessage());

        verify(repository).existsByNic(request.getNic());
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void getCustomer_shouldReturnCustomer_whenExists() {
        String nic = "991234567V";

        CustomerEntity entity = new CustomerEntity(nic, "Manjitha Teshara");

        when(repository.findByNic(nic)).thenReturn(Optional.of(entity));

        CustomerEntity result = service.getCustomer(nic);

        assertNotNull(result);
        assertEquals(nic, result.getNic());
        assertEquals("Manjitha Teshara", result.getFullName());

        verify(repository).findByNic(nic);
    }

    @Test
    void getCustomer_shouldThrowResourceNotFound_whenNotExists() {
        String nic = "991234567V";

        when(repository.findByNic(nic)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getCustomer(nic));

        assertEquals("Customer not found for NIC: 991234567V", ex.getMessage());
        verify(repository).findByNic(nic);
    }
}