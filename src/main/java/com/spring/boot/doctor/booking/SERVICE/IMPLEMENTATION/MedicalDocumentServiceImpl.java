package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import com.spring.boot.doctor.booking.DTOs.MedicalDocumentResponseDto;
import com.spring.boot.doctor.booking.ENTITY.MedicalDocument;
import com.spring.boot.doctor.booking.ENTITY.Patient;
import com.spring.boot.doctor.booking.ENTITY.Users;
import com.spring.boot.doctor.booking.REPOSITORY.MedicalDocumentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.PatientRepository;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;
import com.spring.boot.doctor.booking.SERVICE.MedicalDocumentService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UsersRepository usersRepository;

    private Patient getCurrentPatient() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);
        return patientRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found for user: " + username));
    }

    @Override
    public void uploadDocument(MultipartFile file) {
        Patient patient = getCurrentPatient();

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
    public List<MedicalDocumentResponseDto> getDocumentsOfCurrentPatient() {
        Patient patient = getCurrentPatient();

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

        // Optional: check ownership
        Patient patient = getCurrentPatient();
        if (!document.getPatient().getId().equals(patient.getId())) {
            throw new SecurityException("You do not have permission to delete this document.");
        }

        medicalDocumentRepository.delete(document);
    }
    
    @Override
    public void updateDocument(Long documentId, MultipartFile file) {
        MedicalDocument existingDocument = medicalDocumentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id " + documentId));

        try {
            existingDocument.setFileName(file.getOriginalFilename());
            existingDocument.setFileType(file.getContentType());
            existingDocument.setData(file.getBytes());
            existingDocument.setUploadDate(LocalDateTime.now());
            existingDocument.setUpdatedAt(LocalDateTime.now());

            medicalDocumentRepository.save(existingDocument);

        } catch (IOException e) {
            throw new RuntimeException("Failed to update file data", e);
        }
    }

}
