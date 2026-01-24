package com.banking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByAccountId(Long accountId);
}
