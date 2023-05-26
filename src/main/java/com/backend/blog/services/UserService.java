 package com.backend.blog.services;

import java.util.List;
import java.util.Optional;

import com.backend.blog.dto.UserDTO;
//A service Interface
public interface UserService {
	//method for register new User
	UserDTO registerNewUser(UserDTO user);
	//method for create a User
	UserDTO createUser(UserDTO user);
	//method for update a User by Id
	UserDTO updateUser(UserDTO user, Long userId);
	//method for get all Users
	List<UserDTO> getAllUsers();
	//method for delete a User
	void delete(Long id);
	//method for get a User by Id
	Optional<UserDTO> findOne(Long userId);
}
