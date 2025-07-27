package com.spring.boot.doctor.booking.ENTITY;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String location;
    private String availability; // e.g. "Mon-Fri, 9AM-5PM"

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.ACTIVE;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    public enum AvailabilityStatus {
        ACTIVE, LEAVE, UNAVAILABLE,NO,YES,AVAILABLE
    }

    // Getters and Setters
}
