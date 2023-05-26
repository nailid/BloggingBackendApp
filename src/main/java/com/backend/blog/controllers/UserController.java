package com.backend.blog.controllers;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.blog.dto.UserDTO;
import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.services.UserService;

@RestController
@RequestMapping("/api/blog")
public class UserController {
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	//api for update a User by Id
	@PutMapping("/users/{id}")
	public ResponseEntity<UserDTO> updateUserById(@Valid @RequestBody UserDTO userDTO, 
			@PathVariable("id") Long id)
	{
		log.debug("REST request to update User by id : {}", userDTO);
		UserDTO updatedUser = this.userService.updateUser(userDTO, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	//api for update a User : ADMIN
	@PutMapping("/users")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) throws Exception
	{
		log.debug("REST request to update User : {}", userDTO);
		if (userDTO.getId() == null) {
			throw new Exception("user id not found !!");
		}
		UserDTO result = userService.createUser(userDTO);
		return ResponseEntity.ok().body(result);
	}
	
	//api for get all Users 
	@GetMapping("/users")
	public List<UserDTO> getAllUsers()
	{
		log.debug("REST request to get all Users");
		return userService.getAllUsers();
	}

	//api for get a User by id
	@GetMapping("/users/{id}")
	public Optional<UserDTO> getUser(@PathVariable("id") Long id)
	{
		log.debug("REST request to get User : {}", id);
		Optional<UserDTO> userDto = userService.findOne(id);
		return userDto;
	}
	
	//api for delete a User by id : role specific for admin only
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable ("id") Long id)
	{
		log.debug("REST request to delete User : {}", id);
		this.userService.delete(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully !", true),
				HttpStatus.OK);
	}
}
