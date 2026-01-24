package com.banking.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="accounts")
@Data
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String accountNumber;
	
	private Double balance;
	
	@ManyToOne
	private User user;
	
	private boolean active=true;
}
