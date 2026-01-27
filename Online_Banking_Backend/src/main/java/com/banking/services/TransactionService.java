package com.banking.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.entities.Transaction;
import com.banking.repositories.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	
	public List<Transaction>getTransactions(
			String accountNumber,
			String type,
			LocalDateTime startDate,
			LocalDateTime endDate
			){
		if(type!=null && startDate!=null && endDate!=null) {
			return transactionRepository.findByAccountAccountNumberAndTypeAndTimestampBetween(accountNumber, type, startDate, endDate);
		}
		if(type!=null) {
			return transactionRepository.findByAccountAccountNumberAndType(accountNumber, type);
		}
		if(startDate!=null && endDate!=null) {
			return transactionRepository.findByAccountAccountNumberAndTimestampBetween(accountNumber, startDate, endDate);
		}
		return transactionRepository.findByAccountAccountNumber(accountNumber);
	}
}
