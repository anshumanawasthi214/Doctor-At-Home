package com.spring.boot.doctor.booking.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	
	@Autowired
	UsersRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Usernam not found"));
	}

}
