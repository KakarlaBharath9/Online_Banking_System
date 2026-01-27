package com.banking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String accountNumber;
	
	@Column(nullable=false)
	private Double balance;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private boolean active=true;
}
