package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.DTOs.PatientAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;
import com.spring.boot.doctor.booking.DTOs.PatientResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.ENTITY.Patient;
import com.spring.boot.doctor.booking.ENTITY.Users;
import com.spring.boot.doctor.booking.REPOSITORY.AppointmentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.PatientRepository;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;
import com.spring.boot.doctor.booking.SERVICE.PatientService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public PatientResponseDto registerPatient(PatientRequestDto dto) {
System.out.println(dto);
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

        // Fetch the associated User entity
        Users user = usersRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        patient.setUser(user);

        // Save and map to DTO
        return mapToResponseDto(patientRepository.save(patient));
    }

    @Override
    public PatientResponseDto getPatientByUserId(Long userId) {
        Users user = usersRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Patient patient = patientRepository.findByUser(user)
            .orElseThrow(() -> new EntityNotFoundException("Patient not found for user id: " + userId));

        return mapToResponseDto(patient);
    }

    @Override
    public PatientResponseDto updatePatientByUserId(Long userId, PatientRequestDto dto) {
        Users user = usersRepository.findById(userId).orElseThrow();
        Patient patient = patientRepository.findByUser(user).orElseThrow();

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

        return mapToResponseDto(patientRepository.save(patient));
    }

    @Override
    public void deletePatientByUserId(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow();
        Patient patient = patientRepository.findByUser(user).orElseThrow();
        patientRepository.delete(patient);
    }

    @Override
    public List<AppointmentResponseDto> getBookingHistoryByUserId(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow();
        Patient patient = patientRepository.findByUser(user).orElseThrow();
        return appointmentRepository.findByPatient(patient)
            .stream()
            .map(this::mapToAppointmentResponseDto)
            .collect(Collectors.toList());
    }

    private PatientResponseDto mapToResponseDto(Patient p) {
        PatientResponseDto dto = new PatientResponseDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setEmail(p.getEmail());
        dto.setPhone(p.getPhone());
        dto.setAddress(p.getAddress());
        dto.setDateOfBirth(p.getDateOfBirth());
        dto.setGender(p.getGender());
        dto.setEmergencyContact(p.getEmergencyContact());
        dto.setMedicalHistory(p.getMedicalHistory());
        dto.setBloodGroup(p.getBloodGroup());
        dto.setAllergies(p.getAllergies());
        dto.setProfilePicture(p.getProfilePicture());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());
        return dto;
    }

    private AppointmentResponseDto mapToAppointmentResponseDto(Appointment a) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(a.getId());
        dto.setDoctorId(a.getDoctor().getId());
        dto.setPatientId(a.getPatient().getId());
        dto.setScheduledDateTime(a.getScheduledDateTime());
        dto.setType(a.getType().name());
        dto.setStatus(a.getStatus().name());
        return dto;
    }

	@Override
	public List<PatientAdminDto> getAllPatientsForAdmin() {
		  return patientRepository.findAll()
		            .stream()
		            .map(patient -> new PatientAdminDto(
		                    patient.getId(),
		                    patient.getName(),
		                    patient.getEmail(),
		                    patient.getPhone(),
		                    patient.getAddress(),
		                    patient.getGender(),
		                    patient.getBloodGroup()
		            ))
		            .collect(Collectors.toList());
	}
}
