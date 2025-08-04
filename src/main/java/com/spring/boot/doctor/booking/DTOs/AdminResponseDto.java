package com.spring.boot.doctor.booking.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDto {
	private Long id;
	private String name;
	private String emailId;
	private String mobileNumber;
	
}
