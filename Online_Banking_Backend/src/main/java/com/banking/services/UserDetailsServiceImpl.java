package com.banking.services;

import java.util.List;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.entities.User;
import com.banking.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
	throws UsernameNotFoundException{
		User user = userRepository.findByUsername(username)
				.orElseThrow(()->
				new UsernameNotFoundException("User not found")
				);
		
		//admin approval check
		if(!user.isEnabled()) {
			throw new DisabledException("User not approved by admin");
			
		}
		System.out.println("DB PASSWORD = " + user.getPassword());
		System.out.println(
		  new BCryptPasswordEncoder().matches("admin123", user.getPassword())
		);

		//account Freeze check
		if(!user.isAccountNonLocked()) {
			throw new LockedException("User account is frozen");
		}
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole().name()))
				);
	}
}
