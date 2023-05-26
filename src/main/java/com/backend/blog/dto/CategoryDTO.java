package com.backend.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDTO {
	private Long id;
	@NotBlank
	@Size(min = 4, message = "title must be minimum 4 characters")
	private String title;
	@NotBlank
	@Size(min = 10, message = "description must be minimum 10 characters")
	private String description;
	public CategoryDTO() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
