package com.banking.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entities.Transaction;
import com.banking.services.MonthlyStatementPdfService;
import com.banking.services.TransactionService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.banking.dtos.MonthlyStatementResponse;
import com.banking.dtos.TransactionResponse;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final MonthlyStatementPdfService monthlyStatementPdfService;

    @GetMapping
    public Page<TransactionResponse> getTransactions(
            Authentication authentication,
            @RequestParam String accountNumber,
            @RequestParam(required = false) String type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate,
            Pageable pageable
    ) {

        return transactionService.getTransactions(
                authentication.getName(),
                accountNumber,
                type,
                startDate,
                endDate,
                pageable
        );
    }
    
    @GetMapping("/statement")
    public MonthlyStatementResponse getMonthlyStatement(
            Authentication authentication,
            @RequestParam String accountNumber,
            @RequestParam int month,
            @RequestParam int year
    ) {
        return transactionService.getMonthlyStatement(
                authentication.getName(),
                accountNumber,
                month,
                year
        );
    }
    
    @GetMapping("/monthly/pdf")
    public ResponseEntity<byte[]> downloadMonthlyPdf(
            @RequestParam String accountNumber,
            @RequestParam int month,
            @RequestParam int year,
            Authentication authentication
    ) {

        MonthlyStatementResponse statement =
                transactionService.getMonthlyStatement(
                        authentication.getName(),
                        accountNumber,
                        month,
                        year
                );

        byte[] pdf = monthlyStatementPdfService.generatePdf(statement);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=statement.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }

}
