package com.spring.boot.doctor.booking.DTOs;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class AppointmentRequestDto {
	
	@NotBlank(message="Doctor id Should be Present")
	@NotNull
    private Long doctorId;
	
	@NotBlank(message="Patient id Should be Present")
	@NotNull
    private Long patientId;
	
	@NotBlank(message="Shedule Should be Filled ")
	@NotNull
    private LocalDateTime scheduledDateTime;
	@NotNull
	@NotBlank(message="HomeVisit or Online")
    private String type; // HOME_VISIT or ONLINE

    // Getters and Setters
}
