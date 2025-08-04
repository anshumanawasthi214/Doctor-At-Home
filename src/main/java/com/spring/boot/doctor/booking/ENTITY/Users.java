	package com.spring.boot.doctor.booking.ENTITY;
	
	import java.util.Collection;
	
	import java.util.HashSet;
	import java.util.Set;
	
	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.core.authority.SimpleGrantedAuthority;
	import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
	import jakarta.persistence.EnumType;
	import jakarta.persistence.Enumerated;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.OneToOne;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class Users implements UserDetails{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id;
		private String username;
		private String password;
		
		
		// In User entity:
		@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
		private Patient patient;
		
		@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
		private Doctor doctor;
		
		@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
		private Admin admin;
	  
		@Enumerated(EnumType.STRING)
		private Role role;
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			Set<SimpleGrantedAuthority> authorities=new HashSet<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
			return authorities;
		}
		
		
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password;
		}
	}
