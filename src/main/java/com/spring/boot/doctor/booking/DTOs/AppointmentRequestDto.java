package com.spring.boot.doctor.booking.DTOs;


import java.time.LocalDateTime;



import lombok.Data;
@Data
public class AppointmentRequestDto {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime scheduledDateTime;
    private String type; // HOME_VISIT or ONLINE

    // Getters and Setters
}
