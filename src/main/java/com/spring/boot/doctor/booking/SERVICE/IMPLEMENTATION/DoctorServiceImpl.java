package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.doctor.booking.DOCTOR.ADMIN.DTO.DoctorAdminDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.ENTITY.Doctor.Status;
import com.spring.boot.doctor.booking.REPOSITORY.AppointmentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.DoctorRepository;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;
import com.spring.boot.doctor.bookingDTOs.AppointMentResponseListDto;
import com.spring.boot.doctor.bookingDTOs.DoctorRequestDto;
import com.spring.boot.doctor.bookingDTOs.DoctorResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepo;

    @Override
    public DoctorResponseDto registerDoctor(DoctorRequestDto dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setLocation(dto.getLocation());
        doctor.setAvailability(dto.getAvailability());
        // Default statuses
        doctor.setStatus(Doctor.Status.PENDING);
        doctor.setAvailabilityStatus(Doctor.AvailabilityStatus.ACTIVE);
        Doctor saved = doctorRepository.save(doctor);
        return mapToResponseDto(saved);
    }

    @Override
    public List<DoctorResponseDto> searchDoctors(String specialization, String location) {
        List<Doctor> doctors = doctorRepository.findBySpecializationContainingIgnoreCaseAndLocationContainingIgnoreCaseAndStatusAndAvailabilityStatus(
                specialization, location,
                Doctor.Status.APPROVED,
                Doctor.AvailabilityStatus.ACTIVE);
        return doctors.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAvailabilityStatus(Long doctorId, String status) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        try {
            Doctor.AvailabilityStatus availabilityStatus = Doctor.AvailabilityStatus.valueOf(status.toUpperCase());
            doctor.setAvailabilityStatus(availabilityStatus);
            doctorRepository.save(doctor);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid availability status: " + status);
        }
    }

    private DoctorResponseDto mapToResponseDto(Doctor doctor) {
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setLocation(doctor.getLocation());
        dto.setAvailability(doctor.getAvailability());
        dto.setStatus(doctor.getStatus().name());
        dto.setAvailabilityStatus(doctor.getAvailabilityStatus().name());
        return dto;
    }

	@Override
	public List<DoctorAdminDto> getAllDoctorsForAdmin() {
		 return doctorRepository.findAll()
			        .stream()
			        .map(doc -> new DoctorAdminDto(
			                doc.getId(),
			                doc.getName(),
			                doc.getEmail(),
			                doc.getPhone(),
			                doc.getSpecialization(),
			                doc.getLocation(),
			                doc.getStatus(),
			                doc.getAvailabilityStatus()))
			        .collect(Collectors.toList());
	}

	@Override
	public void updateDoctorStatus(Long doctorId, Status status) {
		 Doctor doctor = doctorRepository.findById(doctorId)
			        .orElseThrow(() -> new RuntimeException("Doctor not found"));

			    doctor.setStatus(status);
			    doctorRepository.save(doctor);
	}

	@Override
	public List<AppointMentResponseListDto> checkAppointmentsRequests(Long doctorId) {
		List<Appointment> appointment=appointmentRepo.findByDoctorId(doctorId);
		List<AppointMentResponseListDto> app=new ArrayList<>();
		for(Appointment apt: appointment) {
				AppointMentResponseListDto aptDto=new AppointMentResponseListDto();
				aptDto.setPatientId(apt.getPatient().getId());
				aptDto.setPatientName(apt.getPatient().getName());
				aptDto.setScheduledDateTime(apt.getScheduledDateTime());
				aptDto.setType(apt.getType().toString());
				aptDto.setStatus(apt.getStatus().toString());
				app.add(aptDto);
		}
		return app;
	}
}
