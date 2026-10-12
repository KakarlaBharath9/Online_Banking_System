package com.banking.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dtos.UserResponse;
import com.banking.entities.Account;
import com.banking.entities.User;
import com.banking.services.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	private final AdminService adminService;
	
	private UserResponse toUserResponse(User user) {
	    return new UserResponse(
	            user.getId(),
	            user.getUsername(),
	            user.getEmail(),
	            user.getRole(),
	            user.isEnabled(),
	            user.isAccountNonLocked()
	    );
	}

	//get all users
	@GetMapping("/users")
	public List<UserResponse> getAllUsers(){
		return adminService.getAllUsers()
				.stream()
				.map(this::toUserResponse)
				.toList();
	}
	
	//approve user
	@PutMapping("/approve/{userId}")
	public User approveUser(@PathVariable Long userId) {
		return adminService.approveUser(userId);
	}
	
	//freeze user
	@PutMapping("/freeze/{userId}")
	public User freezeUser(@PathVariable Long userId) {
		return adminService.freezeUser(userId);
	}
	
	//unfreeze user
	@PutMapping("/unfreeze/{userId}")
	public User unfreezeUser(@PathVariable Long userId) {
		return adminService.unfreezeUser(userId);
	}
	
	// view user accounts
	@GetMapping("/accounts/{userId}")
	public List<Account> getUserAccounts(@PathVariable Long userId){
		return adminService.getUserAccounts(userId);
	}
	
}
