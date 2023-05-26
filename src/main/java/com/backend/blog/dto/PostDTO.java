package com.backend.blog.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PostDTO {
	private Long id;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDTO category;
	private UserDTO user;
	private Set<CommentDTO> comments = new HashSet<>();
	public PostDTO() {}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public CategoryDTO getCategory() {
		return category;
	}
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Set<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}
}
