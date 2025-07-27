package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;


import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.spring.boot.doctor.booking.ENTITY.MedicalDocument;
import com.spring.boot.doctor.booking.ENTITY.Patient;
import com.spring.boot.doctor.booking.REPOSITORY.MedicalDocumentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.PatientRepository;
import com.spring.boot.doctor.booking.SERVICE.MedicalDocumentService;
import com.spring.boot.doctor.bookingDTOs.MedicalDocumentResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalDocumentServiceImpl implements MedicalDocumentService {

    @Autowired
    private MedicalDocumentRepository documentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void uploadDocument(Long patientId, MultipartFile file) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        try {
            MedicalDocument document = new MedicalDocument();
            document.setFileName(file.getOriginalFilename());
            document.setFileType(file.getContentType());
            document.setData(file.getBytes());
            document.setUploadDate(LocalDateTime.now());
            document.setPatient(patient);

            documentRepository.save(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public List<MedicalDocumentResponseDto> getDocumentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        List<MedicalDocument> documents = documentRepository.findByPatient(patient);
        return documents.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private MedicalDocumentResponseDto mapToResponseDto(MedicalDocument document) {
        MedicalDocumentResponseDto dto = new MedicalDocumentResponseDto();
        dto.setId(document.getId());
        dto.setFileName(document.getFileName());
        dto.setFileType(document.getFileType());
        dto.setUploadDate(document.getUploadDate());
        dto.setPatientId(document.getPatient().getId());
        return dto;
    }
}
