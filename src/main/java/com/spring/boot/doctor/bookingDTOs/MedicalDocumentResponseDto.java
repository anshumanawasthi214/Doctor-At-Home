package com.spring.boot.doctor.bookingDTOs;


import java.time.LocalDateTime;

import lombok.Data;


@Data
public class MedicalDocumentResponseDto {
    private Long id;
    private String fileName;
    private String fileType;
    private LocalDateTime uploadDate;
    private Long patientId;

    // Getters and Setters
}
