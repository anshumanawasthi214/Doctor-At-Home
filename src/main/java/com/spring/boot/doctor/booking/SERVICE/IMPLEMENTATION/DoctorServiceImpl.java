package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;


import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;

import com.spring.boot.doctor.booking.DTOs.DoctorAdminDto;


import com.spring.boot.doctor.booking.DTOs.DoctorRequestDto;
import com.spring.boot.doctor.booking.DTOs.DoctorResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.ENTITY.MedicalDocument;
import com.spring.boot.doctor.booking.ENTITY.Users;
import com.spring.boot.doctor.booking.REPOSITORY.AppointmentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.DoctorRepository;
import com.spring.boot.doctor.booking.REPOSITORY.MedicalDocumentRepository;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {


	
	@Autowired
	private AppointmentRepository appointmentRepository;
	    
	
	@Autowired
    private MedicalDocumentRepository medicalDocumentRepository;

	
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private UsersRepository usersRepository;

    
    @Override
    public List<AppointmentResponseDto> getPendingAppointments(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        Doctor doctor=user.getDoctor();
        return appointmentRepository.findByDoctorAndStatus(doctor, Appointment.Status.PENDING)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void respondToAppointment(Long appointmentId, String response) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        Appointment.Status status = Appointment.Status.valueOf(response.toUpperCase());
        if (status != Appointment.Status.ACCEPTED && status != Appointment.Status.REJECTED) {
            throw new IllegalArgumentException("Invalid response. Use ACCEPTED or REJECTED.");
        }

        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    @Override
    public Resource downloadDocument(Long appointmentId, Long documentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        if (appointment.getStatus() != Appointment.Status.ACCEPTED) {
            throw new IllegalStateException("Cannot download document before appointment is accepted");
        }

        MedicalDocument doc = medicalDocumentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        if (!doc.getPatient().getId().equals(appointment.getPatient().getId())) {
            throw new SecurityException("Document does not belong to this patient");
        }

        return new ByteArrayResource(doc.getData());
    }

    private AppointmentResponseDto mapToDto(Appointment appointment) {
        return AppointmentResponseDto.builder()
                .appointmentId(appointment.getId())
                .patientName(appointment.getPatient().getName())
                .scheduledDateTime(appointment.getScheduledDateTime())
                .type(appointment.getType().name())
                .notes(appointment.getNotes())
                .status(appointment.getStatus().name())
                .build();
    }
    
    
    @Override
    public DoctorResponseDto registerDoctor(DoctorRequestDto dto) {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String currentUsername = authentication.getName();

         Users user = usersRepository.findByUsername(currentUsername)
             .orElseThrow(() -> new RuntimeException("User not found: " + currentUsername));

         // Prevent duplicate patient registration
         if (doctorRepository.findByUser(user).isPresent()) {
             throw new RuntimeException("Doctor already registered for this user.");
         }
    	
    		Doctor doctor = new Doctor();
    	    doctor.setName(dto.getName());
    	    doctor.setEmail(dto.getEmail());
    	    doctor.setPhone(dto.getPhone());
    	    doctor.setSpecialization(dto.getSpecialization());
    	    doctor.setLocation(dto.getLocation());
    	    doctor.setAvailability(dto.getAvailability());
    	    doctor.setQualification(dto.getQualification());
    	    doctor.setYearsOfExperience(dto.getYearsOfExperience());
    	    doctor.setConsultationFee(dto.getConsultationFee());
    	    doctor.setProfilePicture(dto.getProfilePicture());
    	    doctor.setLanguages(dto.getLanguages());
    	    doctor.setAbout(dto.getAbout());
    	    doctor.setSpecialties(dto.getSpecialties());

    	    // Optionally, you can set default values for status and availabilityStatus
    	    doctor.setStatus(Doctor.Status.PENDING);
    	    doctor.setAvailabilityStatus(Doctor.AvailabilityStatus.ACTIVE);
    	    
    	    doctor.setUser(user);
            user.setDoctor(doctor);
            
    	    Doctor saved = doctorRepository.save(doctor);
    	    return mapToResponseDto(saved);
    }

    @Override
    public DoctorResponseDto getDoctorById(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Doctor doctor = user.getDoctor();

        if (doctor == null) {
            throw new EntityNotFoundException("No doctor profile linked to user with id " + userId);
        }

        return mapToResponseDto(doctor);
    }

    @Override
    public DoctorResponseDto updateCurrentDoctor(Long userId, DoctorRequestDto dto) {
    	 Users user = usersRepository.findById(userId).orElseThrow();
         Doctor doctor = doctorRepository.findByUser(user).orElseThrow();
        
        if (dto.getName() != null) doctor.setName(dto.getName());
        if (dto.getEmail() != null) doctor.setEmail(dto.getEmail());
        if (dto.getPhone() != null) doctor.setPhone(dto.getPhone());
        if (dto.getSpecialization() != null) doctor.setSpecialization(dto.getSpecialization());
        if (dto.getLocation() != null) doctor.setLocation(dto.getLocation());
        if (dto.getAvailability() != null) doctor.setAvailability(dto.getAvailability());
        if (dto.getQualification() != null) doctor.setQualification(dto.getQualification());
        if (dto.getYearsOfExperience() != null) doctor.setYearsOfExperience(dto.getYearsOfExperience());
        if (dto.getConsultationFee() != null) doctor.setConsultationFee(dto.getConsultationFee());
        if (dto.getProfilePicture() != null) doctor.setProfilePicture(dto.getProfilePicture());
        if (dto.getLanguages() != null) doctor.setLanguages(dto.getLanguages());
        if (dto.getAbout() != null) doctor.setAbout(dto.getAbout());
        if (dto.getSpecialties() != null) doctor.setSpecialties(dto.getSpecialties());
        
        // update status and availabilityStatus if included in DTO
        Doctor updated = doctorRepository.save(doctor);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctorId));
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> searchDoctorsWithFilters(Long id, String name, String specialization, String location,
                                                 Double minFee, Double maxFee, Double minRatings,
                                                 Integer experience, String languages, String availability) {

        Specification<Doctor> spec = (root, query, cb) -> 
            cb.equal(root.get("status"), Doctor.Status.APPROVED); // ✅ only approved

        if (id != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), id));
        }

        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (specialization != null && !specialization.isBlank()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("specialization")), "%" + specialization.toLowerCase() + "%"));
        }

        if (location != null && !location.isBlank()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
        }

        if (minFee != null) {
            spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("consultationFee"), minFee));
        }

        if (maxFee != null) {
            spec = spec.and((root, query, cb) -> 
                cb.lessThanOrEqualTo(root.get("consultationFee"), maxFee));
        }

        if (minRatings != null) {
            spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("ratings"), minRatings));
        }

        if (experience != null) {
            spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("yearsOfExperience"), experience));
        }

        if (languages != null && !languages.isBlank()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("languages")), "%" + languages.toLowerCase() + "%"));
        }

        if (availability != null && !availability.isBlank()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("availability")), "%" + availability.toLowerCase() + "%"));
        }

        return doctorRepository.findAll(spec);
    }


    public DoctorResponseDto mapToResponseDto(Doctor doctor) {
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setId(doctor.getUser().getId());
        dto.setName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setPhone(doctor.getPhone());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setLocation(doctor.getLocation());
        dto.setAvailability(doctor.getAvailability());
        dto.setStatus(doctor.getStatus() != null ? doctor.getStatus().name() : null);
        dto.setAvailabilityStatus(doctor.getAvailabilityStatus() != null ? doctor.getAvailabilityStatus().name() : null);
        dto.setQualification(doctor.getQualification());
        dto.setYearsOfExperience(doctor.getYearsOfExperience());
        dto.setConsultationFee(doctor.getConsultationFee());
        dto.setRatings(doctor.getRatings());
        dto.setProfilePicture(doctor.getProfilePicture());
        dto.setLanguages(doctor.getLanguages());
        dto.setAbout(doctor.getAbout());
        dto.setSpecialties(doctor.getSpecialties());
        dto.setCreatedAt(doctor.getCreatedAt());
        dto.setUpdatedAt(doctor.getUpdatedAt());
        return dto;
    }


	@Override
	public List<DoctorAdminDto> getAllDoctorsForAdmin() {
		 return doctorRepository.findAll().stream()
		            .map(doc -> new DoctorAdminDto(
		            	    doc.getId(),
		            	    doc.getName(),
		            	    doc.getEmail(),
		            	    doc.getPhone(),
		            	    doc.getSpecialization(),
		            	    doc.getLocation(),
		            	    doc.getAvailability(),
		            	    doc.getStatus().name(),
		            	    doc.getAvailabilityStatus().name(),
		            	    doc.getQualification(),
		            	    doc.getYearsOfExperience(),
		            	    doc.getConsultationFee(),
		            	    doc.getSpecialties()
		            	))
		            .collect(Collectors.toList());
	}

	@Override
	public void updateDoctorStatus(Long id, Doctor.Status status) {
		  Doctor doctor = doctorRepository.findById(id)
		            .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + id));
		    
		    doctor.setStatus(status);
		    doctorRepository.save(doctor);
		
	}
	
	


@Override
public DoctorResponseDto getCurrentDoctor() {
    String username = getCurrentUsername();
    Users user = usersRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    Doctor doctor = doctorRepository.findByUser(user)
            .orElseThrow(() -> new EntityNotFoundException("Doctor not found for user " + username));
    return mapToResponseDto(doctor);
}

@Override
public void deleteCurrentDoctor() {
    String username = getCurrentUsername();
    Users user = usersRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    Doctor doctor = doctorRepository.findByUser(user)
            .orElseThrow(() -> new EntityNotFoundException("Doctor not found for user " + username));

    doctorRepository.delete(doctor);
}

//Helper Function
private String getCurrentUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
}

}
