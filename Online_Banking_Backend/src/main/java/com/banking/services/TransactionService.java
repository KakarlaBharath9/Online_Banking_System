package com.banking.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.banking.dtos.TransactionResponse;
import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.TransactionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.banking.dtos.TransactionResponse;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public Page<TransactionResponse> getTransactions(
            String username,
            String accountNumber,
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized account access");
        }

        Page<Transaction> page;

        if (type != null && startDate != null && endDate != null) {
            page = transactionRepository
                    .findByAccountAccountNumberAndTypeAndTimestampBetween(
                            accountNumber, type, startDate, endDate, pageable);
        } else if (type != null) {
            page = transactionRepository
                    .findByAccountAccountNumberAndType(
                            accountNumber, type, pageable);
        } else if (startDate != null && endDate != null) {
            page = transactionRepository
                    .findByAccountAccountNumberAndTimestampBetween(
                            accountNumber, startDate, endDate, pageable);
        } else {
            page = transactionRepository
                    .findByAccountAccountNumber(accountNumber, pageable);
        }

        // ENTITY → DTO mapping
        return page.map(tx -> new TransactionResponse(
                tx.getId(),
                tx.getAccount().getAccountNumber(),
                tx.getAmount(),
                tx.getType(),
                tx.getTimestamp()
        ));
    }
}
