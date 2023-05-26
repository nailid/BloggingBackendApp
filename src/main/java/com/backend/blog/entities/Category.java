package com.backend.blog.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title", length = 100, nullable = false)
	private String title;
	private String description;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Post> posts = new HashSet<>();
	
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	public Category() {
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
