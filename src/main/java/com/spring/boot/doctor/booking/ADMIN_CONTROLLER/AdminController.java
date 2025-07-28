package com.spring.boot.doctor.booking.ADMIN_CONTROLLER;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.boot.doctor.booking.DTOs.DoctorAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientAdminDto;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;
import com.spring.boot.doctor.booking.SERVICE.PatientService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    // ✅ Get all doctors with extended info
    @GetMapping("/get/all/doctors")
    public List<DoctorAdminDto> getAllDoctors() {
        return doctorService.getAllDoctorsForAdmin();
    }

    // ✅ Get all patients with extended info
    @GetMapping("/get/all/patients")
    public List<PatientAdminDto> getAllPatients() {
        return patientService.getAllPatientsForAdmin();
    }

    // ✅ Update doctor status using enum safely
    @PutMapping("/update/doctors/{id}/status")
    public ResponseEntity<String> updateDoctorStatus(
            @PathVariable Long id,
            @RequestParam("status") String statusString) {

        Doctor.Status status;
        try {
            status = Doctor.Status.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status: " + statusString);
        }

        doctorService.updateDoctorStatus(id,status);
        
        return ResponseEntity.ok("Doctor status updated to " + status);
    }
}
