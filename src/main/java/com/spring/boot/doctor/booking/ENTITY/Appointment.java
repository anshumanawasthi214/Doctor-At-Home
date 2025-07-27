	package com.spring.boot.doctor.booking.ENTITY;
	
	
	
	import jakarta.persistence.*;
	import lombok.Data;
	
	import java.time.LocalDateTime;
	
	
	@Data
	@Entity
	@Table(name = "appointments")
	public class Appointment {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    @ManyToOne
	    @JoinColumn(name = "doctor_id")
	    private Doctor doctor;
	
	    @ManyToOne
	    @JoinColumn(name = "patient_id")
	    private Patient patient;
	
	    private LocalDateTime scheduledDateTime;
	
	    @Enumerated(EnumType.STRING)
	    private Type type;
	
	    @Enumerated(EnumType.STRING)
	    private Status status = Status.PENDING;
	
	    public enum Type {
	        HOME_VISIT, ONLINE
	    }
	
	    public enum Status {
	        PENDING, ACCEPTED, REJECTED, COMPLETED
	    }
	
	    // Getters and Setters
	}
