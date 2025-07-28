package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.DTOs.PatientAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;
import com.spring.boot.doctor.booking.DTOs.PatientResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.ENTITY.Patient;
import com.spring.boot.doctor.booking.REPOSITORY.AppointmentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.PatientRepository;
import com.spring.boot.doctor.booking.SERVICE.PatientService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public PatientResponseDto registerPatient(PatientRequestDto dto) {
    	Patient patient = new Patient();
    	patient.setName(dto.getName());
    	patient.setEmail(dto.getEmail());
    	patient.setPhone(dto.getPhone());
    	patient.setAddress(dto.getAddress());
    	patient.setDateOfBirth(dto.getDateOfBirth());
    	patient.setGender(dto.getGender());
    	patient.setEmergencyContact(dto.getEmergencyContact());
    	patient.setMedicalHistory(dto.getMedicalHistory());
    	patient.setBloodGroup(dto.getBloodGroup());
    	patient.setAllergies(dto.getAllergies());
    	patient.setProfilePicture(dto.getProfilePicture());

        // Set other fields if added in DTO
        Patient saved = patientRepository.save(patient);
        return mapToResponseDto(saved);
    }

    @Override
    public PatientResponseDto getPatientById(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));
        return mapToResponseDto(patient);
    }

    @Override
    public PatientResponseDto updatePatient(Long patientId, PatientRequestDto dto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));
        if (dto.getName() != null) patient.setName(dto.getName());
        if (dto.getEmail() != null) patient.setEmail(dto.getEmail());
        if (dto.getPhone() != null) patient.setPhone(dto.getPhone());
        if (dto.getAddress() != null) patient.setAddress(dto.getAddress());
        if (dto.getDateOfBirth() != null) patient.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) patient.setGender(dto.getGender());
        if (dto.getEmergencyContact() != null) patient.setEmergencyContact(dto.getEmergencyContact());
        if (dto.getMedicalHistory() != null) patient.setMedicalHistory(dto.getMedicalHistory());
        if (dto.getBloodGroup() != null) patient.setBloodGroup(dto.getBloodGroup());
        if (dto.getAllergies() != null) patient.setAllergies(dto.getAllergies());
        if (dto.getProfilePicture() != null) patient.setProfilePicture(dto.getProfilePicture());

        // Update other fields here
        Patient updated = patientRepository.save(patient);
        return mapToResponseDto(updated);
    }

    @Override
    public void deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));
        patientRepository.delete(patient);
    }

    @Override
    public List<AppointmentResponseDto> getBookingHistory(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        return appointments.stream()
                .map(this::mapToAppointmentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientAdminDto> getAllPatientsForAdmin() {
    	return patientRepository.findAll()
    	        .stream()
    	        .map(p -> new PatientAdminDto(
    	                p.getId(),
    	                p.getName(),
    	                p.getEmail(),
    	                p.getPhone(),
    	                p.getAddress(),
    	                p.getGender(),
    	                p.getBloodGroup()))
    	        .collect(Collectors.toList());

    }

    private PatientResponseDto mapToResponseDto(Patient patient) {
    	PatientResponseDto dto = new PatientResponseDto();
    	dto.setId(patient.getId());
    	dto.setName(patient.getName());
    	dto.setEmail(patient.getEmail());
    	dto.setPhone(patient.getPhone());
    	dto.setAddress(patient.getAddress());
    	dto.setDateOfBirth(patient.getDateOfBirth());
    	dto.setGender(patient.getGender());
    	dto.setEmergencyContact(patient.getEmergencyContact());
    	dto.setMedicalHistory(patient.getMedicalHistory());
    	dto.setBloodGroup(patient.getBloodGroup());
    	dto.setAllergies(patient.getAllergies());
    	dto.setProfilePicture(patient.getProfilePicture());
    	dto.setCreatedAt(patient.getCreatedAt());
    	dto.setUpdatedAt(patient.getUpdatedAt());

        // Set other fields if any
        return dto;
    }

    private AppointmentResponseDto mapToAppointmentResponseDto(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(appointment.getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setScheduledDateTime(appointment.getScheduledDateTime());
        dto.setType(appointment.getType().name());
        dto.setStatus(appointment.getStatus().name());
        return dto;
    }
}
