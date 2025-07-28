package com.spring.boot.doctor.booking.DTOs;


import java.time.LocalDateTime;



import lombok.Data;


@Data
public class AppointmentResponseDto {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime scheduledDateTime;
    private String type;   // HOME_VISIT or ONLINE
    private String status; // PENDING, ACCEPTED, REJECTED, COMPLETED

    // Getters and Setters
}
