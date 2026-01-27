package com.banking.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	//all transactions for an account
	List<Transaction> findByAccountAccountNumber(String accountNumber);
	
	//filter by type
	List<Transaction> findByAccountAccountNumberAndType(
			String accountNumber,
			String type
			);
	//filter by date range
	List<Transaction> findByAccountAccountNumberAndTimestampBetween(
			String accountNumber,
			LocalDateTime start,
			LocalDateTime end
			);
	//filter by type +date range
	List<Transaction>findByAccountAccountNumberAndTypeAndTimestampBetween(
			String accountNumber,
			String type,
			LocalDateTime start,
			LocalDateTime end
			);
}
