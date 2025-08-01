package com.spring.boot.doctor.booking.ADMIN_CONTROLLER;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.boot.doctor.booking.DTOs.DoctorAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientAdminDto;
import com.spring.boot.doctor.booking.ENTITY.Admin;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.SERVICE.AdminService;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;
import com.spring.boot.doctor.booking.SERVICE.PatientService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    // ----------------- Admin CRUD -----------------

    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin created = adminService.createAdmin(admin);
        return ResponseEntity.ok(created);
    }
    
    @PostMapping("/health")
    public String checkHealth() {
    	return "HealthY";
    }
    
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        Optional<Admin> existing = adminService.getAdminById(id);
        if (existing.isPresent()) {
            admin.setId(id);
            return ResponseEntity.ok(adminService.updateAdmin(admin));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // ----------------- Doctor Management -----------------

    @GetMapping("/get/all/doctors")
    public List<DoctorAdminDto> getAllDoctors() {
        return doctorService.getAllDoctorsForAdmin();
    }

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

        doctorService.updateDoctorStatus(id, status);
        return ResponseEntity.ok("Doctor status updated to " + status);
    }

    // ----------------- Patient Management -----------------

    @GetMapping("/get/all/patients")
    public List<PatientAdminDto> getAllPatients() {
        return patientService.getAllPatientsForAdmin();
    }
}
