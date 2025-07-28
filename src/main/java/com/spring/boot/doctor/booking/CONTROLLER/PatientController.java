package com.spring.boot.doctor.booking.CONTROLLER;

import com.spring.boot.doctor.booking.DTOs.DoctorResponseDto;

import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;
import com.spring.boot.doctor.booking.DTOs.PatientResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.Mapper.DoctorMapper;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;

import com.spring.boot.doctor.booking.SERVICE.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private DoctorMapper doctorMapper;

    // Create/Register new patient
    @PostMapping("/register")
    public ResponseEntity<PatientResponseDto> registerPatient(@RequestBody PatientRequestDto dto) {
        return ResponseEntity.ok(patientService.registerPatient(dto));
    }

    // Get patient by ID
    @GetMapping("/get/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }
    // Update patient details
    @PutMapping("/update/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable Long patientId,
            @RequestBody PatientRequestDto dto) {
        return ResponseEntity.ok(patientService.updatePatient(patientId, dto));
    }

    // Delete patient
    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }

    // Get booking/appointment history
    @GetMapping("/history/{patientId}/appointments")
    public ResponseEntity<?> getBookingHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getBookingHistory(patientId));
    }
    

    // Search doctors with filters
    @GetMapping("/doctors/search")
    public ResponseEntity<List<DoctorResponseDto>> searchDoctors(
            @RequestParam(required = false) Double minFee,
            @RequestParam(required = false) Double maxFee,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) Double minRatings,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer experience,
            @RequestParam(required = false) String languages,
            @RequestParam(required = false) String availability // availability string like "Mon-Fri"
    ) {
        List<Doctor> doctors = doctorService.searchDoctorsWithFilters(
                id, name, specialization, location, minFee, maxFee, minRatings,
                experience, languages, availability
        );

        List<DoctorResponseDto> response = doctors.stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
