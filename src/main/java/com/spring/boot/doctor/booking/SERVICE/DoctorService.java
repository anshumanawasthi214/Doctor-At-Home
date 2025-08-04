package com.spring.boot.doctor.booking.SERVICE;

import java.util.List;

import org.springframework.core.io.Resource;

import com.spring.boot.doctor.booking.DTOs.*;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.ENTITY.Doctor.Status;

public interface DoctorService {

    // 👤 Doctor self-service methods (token-based)
    DoctorResponseDto getCurrentDoctor();
    void deleteCurrentDoctor();

    // ✅ Admin-only methods
    List<DoctorAdminDto> getAllDoctorsForAdmin();
    void updateDoctorStatus(Long doctorId, Status status);

    // 🧑‍⚕️ Public/patient search (only approved doctors)
    List<Doctor> searchDoctorsWithFilters(
        Long id,
        String name,
        String specialization,
        String location,
        Double minFee,
        Double maxFee,
        Double minRatings,
        Integer experience,
        String languages,
        String availability
    );

    // 📥 Registration (used by new doctors)
    DoctorResponseDto registerDoctor(DoctorRequestDto dto);

    // 🔎 Lookup doctor (e.g., for profile view)
    DoctorResponseDto getDoctorById(Long doctorId);

    // ⚠️ Optional — for internal usage or admin override
    DoctorResponseDto mapToResponseDto(Doctor doctor);

    // 📅 Appointment handling
    List<AppointmentResponseDto> getPendingAppointments(Long doctorId);
    void respondToAppointment(Long appointmentId, String response); // "ACCEPTED" or "REJECTED"
    Resource downloadDocument(Long appointmentId, Long documentId);
    public DoctorResponseDto updateCurrentDoctor(Long userId, DoctorRequestDto dto);
    public void deleteDoctor(Long doctorId);
}
