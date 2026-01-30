package com.banking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.entities.Account;
import com.banking.entities.User;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository repo;
	private final UserRepository userRepo;
	
	public Account createAccount(String username, Double initialBalance) {
		if (initialBalance==null) {
			initialBalance=0.0;
		}
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
	
	//deposit logic 
	@Transactional
	public Account deposit(String username, String accountNumber, Double amount) {
		System.out.println("SERVICE AMOUNT = "+amount);
		if(amount==null ||amount<=0) {
			throw new RuntimeException("Deposit amount must be positive");
		}
		
		Account account=repo.findByAccountNumber(accountNumber)
				.orElseThrow(()->new RuntimeException("Account not found"));
		//ensure user owns the account
		if(!account.getUser().getUsername().equals(username)) {
			throw new RuntimeException("Unauthorized account access");
		}
		account.setBalance(account.getBalance()+amount);
		return repo.save(account);
	}
	
	@Transactional
	public Account withdraw(String username, String accountNumber, Double amount) {
		System.out.println("WITHDRAW AMOUNT = "+amount);
		
		if(amount==null || amount<=0) {
			throw new RuntimeException("Withdraw amount must be positive");
		}
		Account account=repo.findByAccountNumber(accountNumber)
				.orElseThrow(()->new RuntimeException("Account not found"));
		
		//ownership check
		if(!account.getUser().getUsername().equals(username)) {
			throw new RuntimeException("Unauthorized account access");
		}
		
		if(account.getBalance()<amount) {
			throw new RuntimeException("Insufficient balance");
		}
		account.setBalance(account.getBalance()-amount);
		
		return repo.save(account);
	}
	
	@Transactional
	public void transfer(String username,
			String fromAccountNumber,
			String toAccountNumber,
			Double amount) {
		System.out.println("TRANSFER AMOUNT = "+amount);
		
		if(amount==null || amount<=0) {
			throw new RuntimeException("Transfer amount must be positive");
		}
		
		if(fromAccountNumber.equals(toAccountNumber)) {
			throw new RuntimeException("Cannot transfer to same account");
		}
		
		Account fromAccount=repo.findByAccountNumber(fromAccountNumber)
				.orElseThrow(()->new RuntimeException("Source account not found"));
		Account toAccount=repo.findByAccountNumber(toAccountNumber)
				.orElseThrow(()->new RuntimeException("Destination account not found"));
		//ownership check(very important)
		if (!fromAccount.getUser().getUsername().equals(username)) {
			throw new RuntimeException("Unauthorized account access");
		}
		if(fromAccount.getBalance()<amount) {
			throw new RuntimeException("Insufficient balance");
		}
		
		//debit
		fromAccount.setBalance(fromAccount.getBalance()-amount);
		//credit
		toAccount.setBalance(toAccount.getBalance()+amount);
		
		repo.save(fromAccount);
		repo.save(toAccount);
	}
	
	private String generateAccountNumber() {
		return "ACC"+System.currentTimeMillis();
	}
}
