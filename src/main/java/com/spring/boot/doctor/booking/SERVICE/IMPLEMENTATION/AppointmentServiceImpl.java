package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import com.spring.boot.doctor.booking.DTOs.AppointmentRequestDto;
import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.ENTITY.Appointment.Status;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.ENTITY.Patient;
import com.spring.boot.doctor.booking.REPOSITORY.AppointmentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.DoctorRepository;
import com.spring.boot.doctor.booking.REPOSITORY.PatientRepository;
import com.spring.boot.doctor.booking.SERVICE.AppointmentService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public AppointmentResponseDto bookAppointment(AppointmentRequestDto dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + dto.getDoctorId()));

        // ✅ Only check if doctor status is APPROVED
        if (doctor.getStatus() != Doctor.Status.APPROVED) {
            throw new IllegalStateException("Doctor is not yet approved for appointments.");
        }

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + dto.getPatientId()));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setScheduledDateTime(dto.getScheduledDateTime());
        appointment.setType(Appointment.Type.valueOf(dto.getType().toUpperCase()));
        appointment.setStatus(Appointment.Status.PENDING);

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponseDto(saved);
    }


    @Override
    public AppointmentResponseDto getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + appointmentId));
        return mapToResponseDto(appointment);
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctorId));
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
        return appointments.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        return appointments.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponseDto updateAppointment(Long appointmentId, AppointmentRequestDto dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + appointmentId));
        if (dto.getScheduledDateTime() != null) {
            appointment.setScheduledDateTime(dto.getScheduledDateTime());
        }
        if (dto.getType() != null) {
            appointment.setType(Appointment.Type.valueOf(dto.getType().toUpperCase()));
        }
        Appointment updated = appointmentRepository.save(appointment);
        return mapToResponseDto(updated);
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + appointmentId));
        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);
    }

    private AppointmentResponseDto mapToResponseDto(Appointment appointment) {
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
	public String changeAppointmentStatus(Long appointmentId, Status status) {
		Appointment appointment=appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + appointmentId));
		
		appointment.setStatus(status);
		appointmentRepository.save(appointment);
		return "Appointment Status for appointment Id "+appointmentId+" Changed to :"+appointment.getStatus();
		
	
	}
}
