package com.banking.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.entities.Role;
import com.banking.entities.User;
import com.banking.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repo;
	private final PasswordEncoder encoder;
	
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(Role.ROLE_CUSTOMER);
		return repo.save(user);
	}
}
