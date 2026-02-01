package com.banking.dtos;

import java.util.List;

import com.banking.entities.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyStatementResponse {
	private String accountNumber;
	private int month;
	private int year;	
	
	private Double openingBalance;
	private Double closingbalance;
	
	private Double totalCredit;
	private Double totalDebit;
	
	private List<TransactionResponse> transactions;
	
	public MonthlyStatementResponse(
			String accountNumber,
			int month,
			int year,
			List<TransactionResponse>transactions
			) {
		this.accountNumber=accountNumber;
		this.month=month;
		this.year=year;
		this.transactions=transactions;
		}
}
