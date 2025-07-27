package com.spring.boot.doctor.booking.CONTROLLER;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.doctor.booking.SERVICE.MedicalDocumentService;
import com.spring.boot.doctor.bookingDTOs.MedicalDocumentResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class MedicalDocumentController {

    @Autowired
    private MedicalDocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                                 @RequestParam Long patientId) {
        documentService.uploadDocument(patientId, file);
        return ResponseEntity.ok("Document uploaded successfully");
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalDocumentResponseDto>> getDocumentsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(documentService.getDocumentsByPatient(patientId));
    }
}
