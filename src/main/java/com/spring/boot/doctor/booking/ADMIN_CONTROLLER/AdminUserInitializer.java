package com.spring.boot.doctor.booking.ADMIN_CONTROLLER;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.boot.doctor.booking.ENTITY.Role;
import com.spring.boot.doctor.booking.ENTITY.Users;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;


@Component
public class AdminUserInitializer {

	@Bean
	public CommandLineRunner createAdminUser(UsersRepository userRepository,PasswordEncoder password) {
		return args->{
			if(userRepository.findByUsername("admin").isEmpty()) {
				Users admin=new Users();
					admin.setUsername("admin");
					admin.setPassword(password.encode("admin1234"));
					admin.setRole(Role.ADMIN); 
					
					userRepository.save(admin);
					System.out.println("Default Admin is Created");
				}
		};
	}
	
		
}
