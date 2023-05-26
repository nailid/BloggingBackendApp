package com.backend.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {
	private Long id;	
	@NotEmpty
	@Size(min = 3, message = "name must be minimum 3 characters")
	private String name;
	@Email(message = "email address is not valid !!")
	private String email;
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be minimum 3 chars and maximum 10 chars")
	private String password;
	@NotEmpty
	private String about;
	public UserDTO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
}
