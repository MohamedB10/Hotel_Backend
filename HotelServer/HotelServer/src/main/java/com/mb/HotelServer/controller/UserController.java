package com.mb.HotelServer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.HotelServer.dto.SignupRequest;
import com.mb.HotelServer.dto.UserDto;
import com.mb.HotelServer.service.UserService;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
		try {
			UserDto createdUser = userService.createUser(signupRequest);
			return new ResponseEntity<>(createdUser, HttpStatus.OK);
		} catch (EntityExistsException entityExistsException) {
			return new ResponseEntity<>("User already exist!", HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
		}
	}

}
