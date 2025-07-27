package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;


import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.ENTITY.Patient;
import com.spring.boot.doctor.booking.REPOSITORY.AppointmentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.PatientRepository;
import com.spring.boot.doctor.booking.SERVICE.PatientService;
import com.spring.boot.doctor.bookingDTOs.AppointmentResponseDto;
import com.spring.boot.doctor.bookingDTOs.PatientAdminDto;
import com.spring.boot.doctor.bookingDTOs.PatientRequestDto;
import com.spring.boot.doctor.bookingDTOs.PatientResponseDto;

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
        Patient saved = patientRepository.save(patient);
        return mapToResponseDto(saved);
    }

    @Override
    public List<AppointmentResponseDto> getBookingHistory(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        return appointments.stream()
                .map(this::mapToAppointmentResponseDto)
                .collect(Collectors.toList());
    }

    private PatientResponseDto mapToResponseDto(Patient patient) {
        PatientResponseDto dto = new PatientResponseDto();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setPhone(patient.getPhone());
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

	@Override
	public List<PatientAdminDto> getAllPatientsForAdmin() {
		return patientRepository.findAll()
		        .stream()
		        .map(doc -> new PatientAdminDto(
		                doc.getId(),
		                doc.getName(),
		                doc.getEmail(),
		                doc.getPhone()))
		        .collect(Collectors.toList());
		
	}
}
