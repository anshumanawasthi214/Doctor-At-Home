package com.spring.boot.doctor.booking.DTOs;

import com.spring.boot.doctor.booking.ENTITY.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
	private String username;
	private String password;
	private Role role;
	
}
