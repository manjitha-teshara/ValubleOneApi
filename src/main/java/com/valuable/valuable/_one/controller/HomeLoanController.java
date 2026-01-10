package com.valuable.valuable._one.controller;

import com.valuable.valuable._one.domain.dto.HomeLoanRequest;
import com.valuable.valuable._one.domain.dto.HomeLoanResponse;
import com.valuable.valuable._one.domain.dto.LoanReleaseRequest;
import com.valuable.valuable._one.service.HomeLoanService;
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
@RequestMapping("/api/home-loans")
public class HomeLoanController {

    private final HomeLoanService service;

    public HomeLoanController(HomeLoanService service) {
        this.service = service;
    }

    /** search loans by refrence, paginated result
     *
     * @param reference
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<HomeLoanResponse>> search(
            @RequestParam(defaultValue = "") String reference,
            Pageable pageable) {
        return ResponseEntity.ok(service.searchLoans(reference, pageable));
    }

    /** release approved loan, send request to backend
     *
     * @param request
     * @return
     */
    @PostMapping("/release-loan")
    public ResponseEntity<Void> releaseLoan(@RequestBody LoanReleaseRequest request) {
        service.releaseLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** update loan status to approved or reject
     *
     * @param loanReference
     * @param status
     * @return
     */
    @PatchMapping("/{loanReference}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String loanReference,
            @RequestParam String status) {
        service.updateLoanStatus(loanReference, status);
        return ResponseEntity.noContent().build();
    }

    /** update requested amount and mark loan pending
     *
     * @param loanReference
     * @param requestedAmount
     * @return
     */
    @PatchMapping("/{loanReference}/requested-amount")
    public ResponseEntity<Void> updateRequestedAmount(
            @PathVariable String loanReference,
            @RequestParam Double requestedAmount) {
        service.updateRequestedAmount(loanReference, requestedAmount);
        return ResponseEntity.noContent().build();
    }

    /** create new home loan for customer, this method take data
     *
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<HomeLoanResponse> createHomeLoan(@RequestBody HomeLoanRequest request) {
        // call service to save home loan
        HomeLoanResponse response = service.createHomeLoan(request); // save new loan
        // return created status and saved object
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

