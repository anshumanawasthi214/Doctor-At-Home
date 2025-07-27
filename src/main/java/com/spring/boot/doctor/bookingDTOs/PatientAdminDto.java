package com.spring.boot.doctor.bookingDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientAdminDto {
	 private Long id;
	    private String name;
	    private String email;
	    private String phone;
}
