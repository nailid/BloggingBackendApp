package com.backend.blog.services;

import java.util.List;
import java.util.Optional;

import com.backend.blog.dto.PostDTO;
import com.backend.blog.payloads.PostResponse;


//A Service Interface
public interface PostService {
	//method for create a Post
	PostDTO addPost(PostDTO postDTO);
	//method for update Post by id
	PostDTO updatePost(PostDTO postDTO, Long postId);
	//method for get all Post
	List<PostDTO> getAllPost();
	//method for get all Post by pagination
	PostResponse getAllPostByPagingSorting(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir) throws Exception;
	//method for get a Post By Id
	PostDTO findOne(Long postId);
	//method for delete a Post By Id
	void delete(Long id);
	
	//custom query method :- get all post by Category
	List<PostDTO> getPostsByCategory(Long catId);
	//custom query method :- get all post by User
	List<PostDTO> getPostsByUser(Long userId);
	//search posts method
	List<PostDTO> searchPosts(String keyword);
}
