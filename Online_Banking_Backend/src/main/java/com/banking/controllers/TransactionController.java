package com.banking.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entities.Transaction;
import com.banking.services.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;
	
	@GetMapping
	public List<Transaction>getTransactions(
			@RequestParam String accountNumber,
			@RequestParam(required=false)String type,
			@RequestParam(required=false)
			@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime startDate,
			@RequestParam(required=false)
			@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime endDate			
			){
		return transactionService.getTransactions(accountNumber, type, startDate, endDate);
	}
	
}
