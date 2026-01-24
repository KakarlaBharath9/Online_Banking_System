package com.banking.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {
	private final AccountRepository accountRepo;
	private final TransactionRepository txRepo;
	
	@Transactional
	public void transfer(String fromAcc, String toAcc, Double amount) {
		Account from = accountRepo.findByAccountNumber(fromAcc).orElseThrow();
		Account to = accountRepo.findByAccountNumber(toAcc).orElseThrow();
		
		if(from.getBalance()<amount)
			throw new RuntimeException("Insufficient balance");
		from.setBalance(from.getBalance()-amount);
		to.setBalance(to.getBalance()+amount);
		
		txRepo.save(new Transaction(null, amount, "DEBIT", LocalDateTime.now(), from));
		txRepo.save(new Transaction(null, amount, "CREDIT", LocalDateTime.now(), to));
	}
}
