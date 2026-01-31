package com.banking.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
	private Long transactionId;
	private String accountNumber;
	private Double amount;
	private String type;
	private LocalDateTime timestamp;
}
