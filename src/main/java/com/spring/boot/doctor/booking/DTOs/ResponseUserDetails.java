package com.spring.boot.doctor.booking.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDetails {
	private Long userId;
	private String username;
	private String password;
	private String role;
}
