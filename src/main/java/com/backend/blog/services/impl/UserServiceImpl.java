package com.backend.blog.services.impl;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.backend.blog.config.AppConstants;
import com.backend.blog.dto.UserDTO;
import com.backend.blog.entities.Role;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.mapper.UserMapper;
import com.backend.blog.repositories.RoleRepository;
import com.backend.blog.repositories.UserRepository;
import com.backend.blog.services.UserService;

import net.bytebuddy.asm.Advice.This;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final UserMapper userMapper ;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepo, UserMapper userMapper, 
			PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
		this.userRepo = userRepo;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.roleRepo = roleRepo;
	}

	@Override
	public UserDTO createUser(UserDTO userDto) {
		log.debug("Request to save User : {}", userDto);
		User user = userMapper.toEntity(userDto);
		user = userRepo.save(user);
		return userMapper.toDto(user);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Long userId) {
		log.debug("Request to update User : {}", userDto, userId);
		User user = this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User ", "User Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		UserDTO userDto1 = userMapper.toDto(updatedUser); 
		return userDto1;
	}

	@Override
	public Optional<UserDTO> findOne(Long userId) {
		log.debug("Request to get User : {}", userId);
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "User Id", userId));
		return userRepo.findById(userId).map(userMapper::toDto);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		log.debug("Request to get all Users");
		return userRepo.findAll().stream().map(userMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}
	
	@Override
	public void delete(Long userId) {
		log.debug("Request to get User : {}", userId);
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "User Id", userId));
		this.userRepo.deleteById(userId);
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDto) {
		User user = userMapper.toEntity(userDto);
		//encoded password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//set roles to new user
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return userMapper.toDto(newUser);
	}

}
