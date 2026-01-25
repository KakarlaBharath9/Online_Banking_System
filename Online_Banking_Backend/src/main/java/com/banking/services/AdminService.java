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
public class AdminService {
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	
	//view all users
	public List<User>getAllUsers(){
		return userRepository.findAll();
	}
	
	//approve user
	public User approveUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User not found"));
		user.setEnabled(true);
		return userRepository.save(user);
	}
	
	//freeze user
	public User freezeUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User not found"));
		
		user.setAccountNonLocked(false);
		return userRepository.save(user);				
	}
	
	//unfreeze user
	public User unfreezeUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User not found"));
		
		user.setAccountNonLocked(true);
		return userRepository.save(user);
	}
	
	//view user accounts
	public List<Account> getUserAccounts(Long userId){
		return accountRepository.findAll()
				.stream()
				.filter(acc->acc.getUser().getId().equals(userId))
				.toList();
	}
	
}
