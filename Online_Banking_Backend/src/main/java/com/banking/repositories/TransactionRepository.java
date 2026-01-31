package com.banking.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	//all transactions for an account
	Page<Transaction> findByAccountAccountNumber(String accountNumber,Pageable pageable);
	
	//filter by type
	Page<Transaction> findByAccountAccountNumberAndType(
			String accountNumber,
			String type,
			Pageable pageable
			);
	//filter by date range
	Page<Transaction> findByAccountAccountNumberAndTimestampBetween(
			String accountNumber,
			LocalDateTime start,
			LocalDateTime end,
			Pageable pageable
			);
	//filter by type +date range
	Page<Transaction>findByAccountAccountNumberAndTypeAndTimestampBetween(
			String accountNumber,
			String type,
			LocalDateTime start,
			LocalDateTime end,
			Pageable pageable
			);
}
