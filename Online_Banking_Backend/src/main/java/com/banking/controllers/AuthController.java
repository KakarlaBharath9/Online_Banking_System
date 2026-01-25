package com.banking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dtos.JwtResponse;
import com.banking.dtos.LoginRequest;
import com.banking.dtos.RegisterRequest;
import com.banking.security.JwtUtil;
import com.banking.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final JwtUtil jwtUtil;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	//register
	@PostMapping("/register")
	public ResponseEntity<String>register(@Valid @RequestBody RegisterRequest request){
		userService.register(request);
		return ResponseEntity.ok("Registration successful. Await admin approval.");
	}
		
	//login
	@PostMapping("/login")
	public JwtResponse login(@RequestBody LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
						)
				);
		String token = jwtUtil.generateToken(request.getUsername());
		return new JwtResponse(token);
	}
}
