package com.spring.boot.doctor.booking.DTOs;

import lombok.Data;

@Data
public class DoctorRequestDto {
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String location;
    private String availability;           // e.g. "Mon-Fri, 9AM-5PM"
    private String qualification;
    private Integer yearsOfExperience;
    private Double consultationFee;
    private String profilePicture;
    private String languages;              // comma-separated or however you handle
    private String about;
    private String specialties;  // consider JSON string or comma-separated
}
