package com.spring.boot.doctor.booking.CONTROLLER;

import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.DTOs.DoctorRequestDto;
import com.spring.boot.doctor.booking.DTOs.DoctorResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.SERVICE.AppointmentService;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private AppointmentService appointmentService;

    // Register doctor
    @PostMapping("/new/register")
    public ResponseEntity<DoctorResponseDto> registerDoctor(@RequestBody DoctorRequestDto dto) {
    	System.out.println(dto);
        return ResponseEntity.ok(doctorService.registerDoctor(dto));
    }

    // Get doctor by ID
    @GetMapping("/get/{doctorId}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }


    // Update doctor details
    @PutMapping("/update/{doctorId}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorRequestDto dto) {
        return ResponseEntity.ok(doctorService.updateDoctor(doctorId, dto));
    }

    // Delete doctor
    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
    
    //Get all appointment Requests
    @GetMapping("/all/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }
    
    //Change appointment status
    @PutMapping("/change/{appointmentId}/status")
    public ResponseEntity<String> changeAppointmentStatus(
    									  @PathVariable Long appointmentId,
    									  @RequestParam String statusString) {
    	
    	   Appointment.Status status;
           try {
               status = Appointment.Status.valueOf(statusString.toUpperCase());
           } catch (IllegalArgumentException e) {
               return ResponseEntity.badRequest().body("Invalid status: " + statusString);
           }
    	String result=appointmentService.changeAppointmentStatus(appointmentId,status);
    	return ResponseEntity.ok(result);
    }
}
