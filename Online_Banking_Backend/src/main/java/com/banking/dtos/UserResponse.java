package com.banking.dtos;

import com.banking.entities.Role;

public record UserResponse(
		Long id,
		String username,
		String email,
		Role role,
		boolean enabled,
		boolean accountNonLocked
) {

}
