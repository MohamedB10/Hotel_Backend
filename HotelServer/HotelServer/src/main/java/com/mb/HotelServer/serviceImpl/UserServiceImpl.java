package com.mb.HotelServer.serviceImpl;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mb.HotelServer.dto.SignupRequest;
import com.mb.HotelServer.dto.UserDto;
import com.mb.HotelServer.entity.User;
import com.mb.HotelServer.enums.UserRole;
import com.mb.HotelServer.repository.UserRepository;
import com.mb.HotelServer.service.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	@PostConstruct
	public void createdAnAdminAccount() {
		Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount.isEmpty()) {
			User user = new User();
			user.setEmail("admin@test.com");
			user.setName("Admin");
			user.setUserRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
			System.out.println("Admin account created successfully");
		} else {
			System.out.println("Admin account already exist");
		}
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public UserDto createUser(SignupRequest signupRequest) {
		if(userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
			throw new EntityExistsException("User Already Present With email " + signupRequest.getEmail());
		}
		
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setUserRole(UserRole.CUSTOMER);
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		
		User createdUser = userRepository.save(user);
		return createdUser.getUserDto();
	}

}
