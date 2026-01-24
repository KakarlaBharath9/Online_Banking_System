package com.banking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.entities.Account;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository repo;
	private final UserRepository userRepo;
	
	public List<Account> getUserAccounts(String username){
		return repo.findByUserUsername(username);
	}
}
