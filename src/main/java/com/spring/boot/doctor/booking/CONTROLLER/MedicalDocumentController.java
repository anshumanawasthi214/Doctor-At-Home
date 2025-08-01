package com.spring.boot.doctor.booking.CONTROLLER;

import com.spring.boot.doctor.booking.DTOs.MedicalDocumentResponseDto;
import com.spring.boot.doctor.booking.SERVICE.MedicalDocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class MedicalDocumentController {

    @Autowired
    private MedicalDocumentService documentService;

    // Upload document for a patient
    @PostMapping("/upload/{patientId}")
    public ResponseEntity<Void> uploadDocument(
            @PathVariable Long patientId,
            @RequestParam("file") MultipartFile file) {
        documentService.uploadDocument(patientId, file);
        return ResponseEntity.ok().build();
    }

    // Get documents by patient ID
    @PostAuthorize("returnObject.performedBy==authentication.name")
    @GetMapping("/get/patient/{patientId}")
    public ResponseEntity<List<MedicalDocumentResponseDto>> getDocumentsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(documentService.getDocumentsByPatient(patientId));
    }

    // Delete document by ID (optional, if implemented)
    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
