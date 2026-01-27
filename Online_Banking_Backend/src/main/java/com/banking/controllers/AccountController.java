package com.banking.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dtos.CreateAccountRequest;
import com.banking.entities.Account;
import com.banking.services.AccountService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
	//get user accounts
	@GetMapping
	public List<Account>getAccounts(Authentication auth){
		return service.getUserAccounts(auth.getName());
	}
}
