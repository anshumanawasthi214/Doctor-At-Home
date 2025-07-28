package com.spring.boot.doctor.booking.DTOs;

import lombok.Data;

@Data
public class DoctorAvailabilityUpdateDto {
    private String availabilityStatus; // ACTIVE, LEAVE, UNAVAILABLE, etc.
}
