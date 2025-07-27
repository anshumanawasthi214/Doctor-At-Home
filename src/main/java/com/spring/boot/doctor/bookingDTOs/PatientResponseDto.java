package com.spring.boot.doctor.bookingDTOs;

import lombok.Data;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;

    // Getters and Setters
}
