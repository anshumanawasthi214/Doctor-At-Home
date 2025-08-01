package com.spring.boot.doctor.booking.CONFIG;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.boot.doctor.booking.FILTER.JWTAuthFilter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private  UserDetailsService userDetailsService;
	
	@Autowired
	JWTAuthFilter jwtFilter;

	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable()) 
        .authorizeHttpRequests(auth -> auth
        	.requestMatchers("/error", "/v3/api-docs/**",
        		    "/swagger-ui/**",
        		    "/swagger-ui.html",
        		    "/api/register",
        		    "/authenticate",
        		    "/user/register/doctor",
        		    "/user/register/patient",
        		    "/user/check")
        	.permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .requestMatchers("/api/doctor/**").hasRole("DOCTOR")
            .requestMatchers("/api/patient/**").hasRole("PATIENT")
            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
    return http.build();
}	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	 public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		
	    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    builder.userDetailsService(userDetailsService)
	           .passwordEncoder(passwordEncoder());
	    return builder.build();
    }
	

}

