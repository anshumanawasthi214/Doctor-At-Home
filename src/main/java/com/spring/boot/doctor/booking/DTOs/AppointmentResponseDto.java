package com.spring.boot.doctor.booking.DTOs;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {
    private Long appointmentId;
    private Long patientId;
    private String patientName;
    private LocalDateTime scheduledDateTime;
    private String type;
    private String status;
    private String notes;
}