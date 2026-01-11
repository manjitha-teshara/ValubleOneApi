package com.valuable.valuable._one.controller;

import com.valuable.valuable._one.domain.dto.HomeLoanRequest;
import com.valuable.valuable._one.domain.dto.HomeLoanResponse;
import com.valuable.valuable._one.domain.dto.LoanReleaseRequest;
import com.valuable.valuable._one.service.HomeLoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/api/home-loans")
@Tag(name = "Home Loans", description = "Home loan creation, search, approval and release")
public class HomeLoanController {

    private final HomeLoanService service;

    public HomeLoanController(HomeLoanService service) {
        this.service = service;
    }

    @Operation(
            summary = "Search home loans",
            description = "Search home loans by loan reference with pagination support"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping
    public ResponseEntity<Page<HomeLoanResponse>> search(
            @Parameter(description = "Loan reference (partial or full)")
            @RequestParam(defaultValue = "") String reference,
            Pageable pageable) {
        return ResponseEntity.ok(service.searchLoans(reference, pageable));
    }

    @Operation(
            summary = "Create a new home loan",
            description = "Creates a new home loan for a customer"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Loan created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<HomeLoanResponse> createHomeLoan(
            @RequestBody @Valid HomeLoanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createHomeLoan(request));
    }

    @Operation(
            summary = "Release an approved loan",
            description = "Releases loan amount by notifying downstream systems"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Loan released successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "409", description = "Loan not approved")
    })
    @PostMapping("/release-loan")
    public ResponseEntity<Void> releaseLoan(
            @RequestBody LoanReleaseRequest request) {
        service.releaseLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Update loan status",
            description = "Approve or reject a loan"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status updated"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    @PatchMapping("/{loanReference}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String loanReference,
            @Parameter(description = "Loan status (APPROVED or REJECTED)")
            @RequestParam String status) {
        service.updateLoanStatus(loanReference, status);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update requested loan amount",
            description = "Updates loan amount and marks loan as PENDING"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Amount updated"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    @PatchMapping("/{loanReference}/requested-amount")
    public ResponseEntity<Void> updateRequestedAmount(
            @PathVariable String loanReference,
            @Parameter(description = "New requested loan amount")
            @RequestParam Double requestedAmount) {
        service.updateRequestedAmount(loanReference, requestedAmount);
        return ResponseEntity.noContent().build();
    }
}


