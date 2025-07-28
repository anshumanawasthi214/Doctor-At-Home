package com.spring.boot.doctor.booking.REPOSITORY;

import com.spring.boot.doctor.booking.ENTITY.MedicalDocument;
import com.spring.boot.doctor.booking.ENTITY.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalDocumentRepository extends JpaRepository<MedicalDocument, Long> {

    List<MedicalDocument> findByPatient(Patient patient);
}
