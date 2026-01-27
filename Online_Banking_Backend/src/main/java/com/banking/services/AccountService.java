package com.banking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.entities.Account;
import com.banking.entities.User;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository repo;
	private final UserRepository userRepo;
	
	public Account createAccount(String username, Double initialBalance) {
		User user=userRepo.findByUsername(username)
				.orElseThrow(()->new RuntimeException("User not found"));
		Account account=new Account();
		account.setAccountNumber(generateAccountNumber());
		account.setBalance(initialBalance);
		account.setUser(user);
		account.setActive(true);
		
		return repo.save(account);
	}
	
	public List<Account> getUserAccounts(String username){
		return repo.findByUserUsername(username);
	}
	private String generateAccountNumber() {
		return "ACC"+System.currentTimeMillis();
	}
}
