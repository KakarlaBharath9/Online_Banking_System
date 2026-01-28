package com.banking.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.banking.dtos.TransferRequest;
import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void transferMoney(TransferRequest request) {

        Account from = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account to = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (from.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());

        transactionRepository.save(
                new Transaction(null, request.getAmount(), "DEBIT",
                        LocalDateTime.now(), from)
        );

        transactionRepository.save(
                new Transaction(null, request.getAmount(), "CREDIT",
                        LocalDateTime.now(), to)
        );
    }
}
