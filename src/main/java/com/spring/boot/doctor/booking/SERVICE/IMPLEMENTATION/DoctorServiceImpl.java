package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;


import com.spring.boot.doctor.booking.DTOs.DoctorAdminDto;

import com.spring.boot.doctor.booking.DTOs.DoctorRequestDto;
import com.spring.boot.doctor.booking.DTOs.DoctorResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.REPOSITORY.DoctorRepository;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorResponseDto registerDoctor(DoctorRequestDto dto) {
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

    	    Doctor saved = doctorRepository.save(doctor);
    	    return mapToResponseDto(saved);
    }

    @Override
    public DoctorResponseDto getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctorId));
        return mapToResponseDto(doctor);
    }

    @Override
    public DoctorResponseDto updateDoctor(Long doctorId, DoctorRequestDto dto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctorId));
        
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

        Specification<Doctor> spec = null;

        if (id != null) {
            spec = (root, query, cb) -> cb.equal(root.get("id"), id);
        }

        if (name != null && !name.isBlank()) {
            Specification<Doctor> nameSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            spec = (spec == null) ? nameSpec : spec.and(nameSpec);
        }

        if (specialization != null && !specialization.isBlank()) {
            Specification<Doctor> specializationSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("specialization")), "%" + specialization.toLowerCase() + "%");
            spec = (spec == null) ? specializationSpec : spec.and(specializationSpec);
        }

        if (location != null && !location.isBlank()) {
            Specification<Doctor> locationSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
            spec = (spec == null) ? locationSpec : spec.and(locationSpec);
        }

        if (minFee != null) {
            Specification<Doctor> minFeeSpec = (root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("consultationFee"), minFee);
            spec = (spec == null) ? minFeeSpec : spec.and(minFeeSpec);
        }

        if (maxFee != null) {
            Specification<Doctor> maxFeeSpec = (root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("consultationFee"), maxFee);
            spec = (spec == null) ? maxFeeSpec : spec.and(maxFeeSpec);
        }

        if (minRatings != null) {
            Specification<Doctor> minRatingsSpec = (root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("ratings"), minRatings);
            spec = (spec == null) ? minRatingsSpec : spec.and(minRatingsSpec);
        }

        if (experience != null) {
            Specification<Doctor> experienceSpec = (root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("yearsOfExperience"), experience);
            spec = (spec == null) ? experienceSpec : spec.and(experienceSpec);
        }

        if (languages != null && !languages.isBlank()) {
            Specification<Doctor> languageSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("languages")), "%" + languages.toLowerCase() + "%");
            spec = (spec == null) ? languageSpec : spec.and(languageSpec);
        }

        if (availability != null && !availability.isBlank()) {
            Specification<Doctor> availabilitySpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("availability")), "%" + availability.toLowerCase() + "%");
            spec = (spec == null) ? availabilitySpec : spec.and(availabilitySpec);
        }

        return doctorRepository.findAll(spec);
    }


    private DoctorResponseDto mapToResponseDto(Doctor doctor) {
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setId(doctor.getId());
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
}
