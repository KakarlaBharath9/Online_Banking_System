package com.banking.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dtos.CreateAccountRequest;
import com.banking.dtos.DepositRequest;
import com.banking.entities.Account;
import com.banking.services.AccountService;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService service;
	
	//create account
	@PostMapping("/create")
	public Account createAccount(
			@RequestBody CreateAccountRequest request,
			Authentication authentication
			) {
		return service.createAccount(
				authentication.getName(),
				request.getInitialBalance()
				);
				
	}
	//deposit api
	@PostMapping("/deposit")
	public Account deposit(
	        @AuthenticationPrincipal UserDetails userDetails,
	        @RequestBody DepositRequest request
	) {

	    System.out.println("CONTROLLER AMOUNT = " + request.getAmount());

	    return service.deposit(
	            userDetails.getUsername(),
	            request.getAccountNumber(),
	            request.getAmount()
	    );
	}

	//get user accounts
	@GetMapping
	public List<Account>getAccounts(Authentication auth){
		return service.getUserAccounts(auth.getName());
	}
}
