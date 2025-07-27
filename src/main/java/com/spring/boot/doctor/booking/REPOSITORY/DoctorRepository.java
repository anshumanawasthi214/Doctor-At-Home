package com.spring.boot.doctor.booking.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.doctor.booking.ENTITY.Doctor;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecializationContainingIgnoreCaseAndLocationContainingIgnoreCaseAndStatusAndAvailabilityStatus(
	        String specialization,
	        String location,
	        Doctor.Status status,
	        Doctor.AvailabilityStatus availabilityStatus);

}