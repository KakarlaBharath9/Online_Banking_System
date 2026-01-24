package com.banking.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="transactions")
@Data
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Double amount;
	private String type; //CREDIT //DEBIT
	private LocalDateTime timestamp;
	@ManyToOne
	private Account account;
}
