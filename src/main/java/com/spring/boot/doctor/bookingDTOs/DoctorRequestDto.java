package com.spring.boot.doctor.bookingDTOs;

import lombok.Data;

@Data
public class DoctorRequestDto {
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String location;
    private String availability;

    // Getters and Setters
}
