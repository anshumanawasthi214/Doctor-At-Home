package com.spring.boot.doctor.booking.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.ENTITY.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

	List<Doctor> findBySpecializationContainingIgnoreCaseAndLocationContainingIgnoreCaseAndStatusAndAvailabilityStatus(
	        String specialization,
	        String location,
	        Doctor.Status status,
	        Doctor.AvailabilityStatus availabilityStatus);

	Optional<Doctor> findByUser(Users user);

}