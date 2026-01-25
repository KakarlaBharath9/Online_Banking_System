package com.banking.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.dtos.RegisterRequest;
import com.banking.entities.Role;
import com.banking.entities.User;
import com.banking.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public User register(RegisterRequest request) {
		if(userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new RuntimeException("Username already exists");
		}
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		//banking defaults
		user.setRole(Role.ROLE_CUSTOMER);
		user.setEnabled(false); //admin approval required
		user.setAccountNonLocked(true);
		
		return userRepository.save(user);
	}
}
