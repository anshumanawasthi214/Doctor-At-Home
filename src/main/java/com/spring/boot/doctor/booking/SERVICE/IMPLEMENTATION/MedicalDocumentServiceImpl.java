package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import com.spring.boot.doctor.booking.DTOs.MedicalDocumentResponseDto;
import com.spring.boot.doctor.booking.ENTITY.MedicalDocument;
import com.spring.boot.doctor.booking.ENTITY.Patient;
import com.spring.boot.doctor.booking.REPOSITORY.MedicalDocumentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.PatientRepository;
import com.spring.boot.doctor.booking.SERVICE.MedicalDocumentService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalDocumentServiceImpl implements MedicalDocumentService {

    @Autowired
    private MedicalDocumentRepository medicalDocumentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void uploadDocument(Long patientId, MultipartFile file) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));

        MedicalDocument document = new MedicalDocument();
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        try {
            document.setData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file data", e);
        }
        document.setUploadDate(LocalDateTime.now());
        document.setPatient(patient);

        medicalDocumentRepository.save(document);
    }

    @Override
    public List<MedicalDocumentResponseDto> getDocumentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));

        List<MedicalDocument> documents = medicalDocumentRepository.findByPatient(patient);
        return documents.stream()
        .map(doc -> new MedicalDocumentResponseDto(
                doc.getId(),
                doc.getFileName(),
                doc.getFileType(),
                doc.getUploadDate(),
                doc.getCreatedAt(),
                doc.getUpdatedAt(),
                doc.getPatient().getId()))
        .collect(Collectors.toList());
    }

    @Override
    public void deleteDocument(Long documentId) {
        MedicalDocument document = medicalDocumentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id " + documentId));
        medicalDocumentRepository.delete(document);
    }
}
