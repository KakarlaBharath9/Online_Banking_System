package com.banking.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Double amount;
	private String type; //CREDIT //DEBIT
	private LocalDateTime timestamp;
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
}
