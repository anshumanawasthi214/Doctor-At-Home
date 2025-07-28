package com.spring.boot.doctor.booking.SERVICE;

import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.doctor.booking.DTOs.MedicalDocumentResponseDto;

import java.util.List;

public interface MedicalDocumentService {

    void uploadDocument(Long patientId, MultipartFile file);

    List<MedicalDocumentResponseDto> getDocumentsByPatient(Long patientId);

    void deleteDocument(Long documentId); // Optional if implemented
}
