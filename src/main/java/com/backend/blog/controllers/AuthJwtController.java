package com.backend.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.dto.UserDTO;
import com.backend.blog.security.jwtImpl.JwtAuthRequest;
import com.backend.blog.security.jwtImpl.JwtAuthResponse;
import com.backend.blog.security.jwtImpl.JwtTokenHelper;
import com.backend.blog.services.UserService;

@RestController
@RequestMapping("/api/auth/blog")
public class AuthJwtController {
	private final JwtTokenHelper jwtTokenHelper;
	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	
	public AuthJwtController(JwtTokenHelper jwtTokenHelper, UserService userService,
			UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
		super();
		this.jwtTokenHelper = jwtTokenHelper;
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request){
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		this.authenticationManager.authenticate(authenticationToken);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
		UserDTO registeredUser = this.userService.registerNewUser(userDTO);
		return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
	}
}
