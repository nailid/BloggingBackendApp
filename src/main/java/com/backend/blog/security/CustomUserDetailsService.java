package com.backend.blog.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading user from db
		User user =
		this.userRepository.findByEmail(username).orElseThrow(()->new 
				ResourceNotFoundException("User ", " email "+username, 0));
		return user;
	}
}
