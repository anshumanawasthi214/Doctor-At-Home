package com.spring.boot.doctor.booking.DTOs;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentRequestDto {

    @NotNull(message = "Doctor ID must be provided")
    private Long doctorId;

    @NotNull(message = "Scheduled date and time must be provided")
    private LocalDateTime scheduledDateTime;

    @NotNull(message = "Appointment type must be provided")
    private String type; // HOME_VISIT, ONLINE, CLINIC
}
