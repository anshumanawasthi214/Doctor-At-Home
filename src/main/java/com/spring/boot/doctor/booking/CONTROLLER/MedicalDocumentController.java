package com.spring.boot.doctor.booking.CONTROLLER;

import com.spring.boot.doctor.booking.DTOs.MedicalDocumentResponseDto;
import com.spring.boot.doctor.booking.SERVICE.MedicalDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class MedicalDocumentController {

    @Autowired
    private MedicalDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadDocument(@RequestParam("file") MultipartFile file) {
    	  System.out.println("File name received: " + file.getOriginalFilename());
        documentService.uploadDocument(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-documents")
    public ResponseEntity<List<MedicalDocumentResponseDto>> getDocumentsOfCurrentPatient() {
        return ResponseEntity.ok(documentService.getDocumentsOfCurrentPatient());
    }

    @PutMapping("/update/{documentId}")
    public ResponseEntity<Void> updateDocument(
            @PathVariable Long documentId,
            @RequestParam("file") MultipartFile file) {
        documentService.updateDocument(documentId, file);
        return ResponseEntity.ok().build();
    }

    
    
    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }	
}
