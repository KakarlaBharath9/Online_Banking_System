package com.banking.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dtos.JwtResponse;
import com.banking.dtos.LoginRequest;
import com.banking.security.JwtUtil;
import com.banking.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final JwtUtil jwtUtil;
	private final UserService service;
	
	@PostMapping("/login")
	public JwtResponse login(@RequestBody LoginRequest request) {
		String token = jwtUtil.generateToken(request.getUsername());
		return new JwtResponse(token);
	}
}
