package com.banking.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByUserUsername(String username);
	Optional<Account> findByAccountNumber(String accountNumber);
}
