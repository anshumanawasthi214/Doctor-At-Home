package com.spring.boot.doctor.booking.ADMIN_CONTROLLER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.doctor.booking.DOCTOR.ADMIN.DTO.DoctorAdminDto;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;
import com.spring.boot.doctor.booking.SERVICE.PatientService;
import com.spring.boot.doctor.bookingDTOs.PatientAdminDto;

@RestController
@RequestMapping("/admin/doctors")
public class AdminDoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;
    
    

    @GetMapping
    public List<DoctorAdminDto> getAllDoctors() {
        return doctorService.getAllDoctorsForAdmin();
    }
    
    @GetMapping("/all/patients")
    public List<PatientAdminDto> getAllPatients() {
        return patientService.getAllPatientsForAdmin();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateDoctorStatus(
            @PathVariable Long id,
            @RequestParam Doctor.Status status) {
        doctorService.updateDoctorStatus(id, status);
        return ResponseEntity.ok("Doctor status updated to " + status);
    }
}
