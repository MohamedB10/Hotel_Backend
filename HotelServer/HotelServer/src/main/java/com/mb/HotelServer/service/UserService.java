package com.mb.HotelServer.service;

import org.springframework.stereotype.Service;

import com.mb.HotelServer.dto.SignupRequest;
import com.mb.HotelServer.dto.UserDto;

@Service
public interface UserService {
	
	UserDto createUser(SignupRequest signupRequest);

}
