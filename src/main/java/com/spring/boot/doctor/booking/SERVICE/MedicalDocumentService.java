package com.spring.boot.doctor.booking.SERVICE;

import org.springframework.web.multipart.MultipartFile;


import com.spring.boot.doctor.booking.DTOs.MedicalDocumentResponseDto;

import java.util.List;

public interface MedicalDocumentService {
    void uploadDocument(MultipartFile file);
    List<MedicalDocumentResponseDto> getDocumentsOfCurrentPatient();
    void deleteDocument(Long documentId);
    public void updateDocument(Long documentId, MultipartFile file);
}
