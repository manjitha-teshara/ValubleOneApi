package com.valuable.valuable._one.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.valuable.valuable._one.domain.dto.CustomerRequest;
import com.valuable.valuable._one.domain.dto.CustomerResponse;
import com.valuable.valuable._one.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {

        // given
        CustomerRequest request = new CustomerRequest();
        request.setNic("991234567V");
        request.setFullName("Manjitha Teshara");

        CustomerResponse response = new CustomerResponse(1L,"991234567V", "Manjitha Teshara");

        Mockito.when(customerService.createCustomer(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(post("/v1/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nic").value("991234567V"))
                .andExpect(jsonPath("$.fullName").value("Manjitha Teshara"));
    }
}