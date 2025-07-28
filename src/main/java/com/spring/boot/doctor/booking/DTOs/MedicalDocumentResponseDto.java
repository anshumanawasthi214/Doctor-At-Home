package com.spring.boot.doctor.booking.DTOs;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalDocumentResponseDto {
    private Long id;
    private String fileName;
    private String fileType;
    private LocalDateTime uploadDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long patientId;
}
