package com.valuable.valuable._one.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.valuable.valuable._one.domain.dto.HomeLoanRequest;
import com.valuable.valuable._one.domain.dto.HomeLoanResponse;
import com.valuable.valuable._one.domain.dto.LoanReleaseRequest;
import com.valuable.valuable._one.domain.enums.LoanStatus;
import com.valuable.valuable._one.service.HomeLoanService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(HomeLoanController.class)
class HomeLoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeLoanService homeLoanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void searchLoans_success() throws Exception {
        HomeLoanResponse response =
                new HomeLoanResponse("HL-001", new BigDecimal("500000"), "John Doe", LoanStatus.APPROVED);

        Page<HomeLoanResponse> page = new PageImpl<>(List.of(response));

        Mockito.when(homeLoanService.searchLoans(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/v1/api/home-loans")
                        .param("reference", "HL")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].loanReference").value("HL-001"));
    }

    @Test
    void createHomeLoan_success() throws Exception {
        HomeLoanRequest request = new HomeLoanRequest(
                "991234567V",
               "1400000",
                new BigDecimal("450000")
        );

        HomeLoanResponse response =
                new HomeLoanResponse("HL-002", new BigDecimal("400000"), "Jane Doe", LoanStatus.PENDING);

        Mockito.when(homeLoanService.createHomeLoan(any()))
                .thenReturn(response);

        mockMvc.perform(post("/v1/api/home-loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.loanReference").value("HL-002"));
    }

    @Test
    void releaseLoan_success() throws Exception {
        LoanReleaseRequest request =
                new LoanReleaseRequest("HL-001", new BigDecimal("120000"), 777888L);

        mockMvc.perform(post("/v1/api/home-loans/release-loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(homeLoanService).releaseLoan(any());
    }

    @Test
    void updateLoanStatus_success() throws Exception {
        mockMvc.perform(patch("/v1/api/home-loans/HL-001/status")
                        .param("status", "APPROVED"))
                .andExpect(status().isNoContent());

        Mockito.verify(homeLoanService)
                .updateLoanStatus("HL-001", "APPROVED");
    }

    @Test
    void updateRequestedAmount_success() throws Exception {
        mockMvc.perform(patch("/v1/api/home-loans/HL-001/requested-amount")
                        .param("requestedAmount", "350000"))
                .andExpect(status().isNoContent());

        Mockito.verify(homeLoanService)
                .updateRequestedAmount("HL-001", 350000.0);
    }
}