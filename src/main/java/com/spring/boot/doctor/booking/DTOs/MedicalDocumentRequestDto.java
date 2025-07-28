package com.spring.boot.doctor.booking.DTOs;

import lombok.Data;

@Data
public class MedicalDocumentRequestDto {
    private String fileName;
    private String fileType;
    private Long patientId;
    // The actual file data (byte[]) usually handled separately (e.g., MultipartFile in controller)
}
