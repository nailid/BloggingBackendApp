 package com.backend.blog.services.impl;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.blog.dto.PostDTO;
import com.backend.blog.entities.Category;
import com.backend.blog.entities.Post;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.mapper.PostMapper;
import com.backend.blog.payloads.PostResponse;
import com.backend.blog.repositories.CategoryRepository;
import com.backend.blog.repositories.PostRepository;
import com.backend.blog.repositories.UserRepository;
import com.backend.blog.services.PostService;


@Service
@Transactional
public class PostServiceImpl implements PostService {
	private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
	private final PostRepository postRepository;
	private final PostMapper postMapper;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	
	public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, CategoryRepository categoryRepository, UserRepository userRepository) {
		super();
		this.postRepository = postRepository;
		this.postMapper = postMapper;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
	}

	@Override
	public PostDTO addPost(PostDTO postDTO) {
		log.debug("Request to save Post : {}", postDTO);
		Post post = postMapper.toEntity(postDTO);
		post.setAddedDate(new Date());
		Post newPost = this.postRepository.save(post);
		return postMapper.toDto(post);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Long postId) {
		log.debug("Request to update Post by id : {}", postDTO, postId);
		Post post = this.postRepository.findById(postId).orElseThrow(()->
		new ResourceNotFoundException("Post ", "Post Id", postId));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		post.setAddedDate(postDTO.getAddedDate());
		/*
		 * post.setCategory(postDTO.getCategory());
		 * //post.setCategory(postDTO.getCategory()); //post.setUser(postDTO.getUser());
		 */		Post updatedPost = this.postRepository.save(post);
		PostDTO result = postMapper.toDto(updatedPost);
		return result;
	}
	
	@Override
	public PostResponse getAllPostByPagingSorting(Integer pageNumber, Integer pageSize, 
			String sortBy, String sortDir) throws Exception {
		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if (sortDir.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		} else {
		throw new Exception("enter wrong Char!!");	
		}
		log.debug("Request to get all Post with pagination");
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepository.findAll(pageable);
		List<Post> allPosts = pagePost.getContent();
		List<PostDTO> postDTO = allPosts.stream().map(postMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDTO);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setFirstPage(pagePost.isFirst());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDTO findOne(Long postId) {
		log.debug("Request to get Post : {}", postId);
		Post post = this.postRepository.findById(postId).orElseThrow(()->
		new ResourceNotFoundException("Post ", "Post Id", postId));
		return this.postMapper.toDto(post);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Post : {}", id);
		Post post = this.postRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Post ", "Post Id", id));
		this.postRepository.deleteById(id);
	}

	@Override
	public List<PostDTO> getPostsByCategory(Long catId) {
		Category category = this.categoryRepository.findById(catId).orElseThrow(()->
		new ResourceNotFoundException("Category ", "Category Id", catId));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDTO> postDTO = posts.stream().map(postMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
		return postDTO;
	}

	@Override
	public List<PostDTO> getPostsByUser(Long userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->
		new ResourceNotFoundException("User ", "User Id", userId));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDTO> postDTO = posts.stream().map(postMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
		return postDTO;
	}

	@Override
	public List<PostDTO> getAllPost() {
		log.debug("Request to get all Post");
		return postRepository.findAll().stream().map(postMapper::toDto).
				collect(Collectors.toCollection(LinkedList::new));
	}
	
	@Override
	public List<PostDTO> searchPosts(String keyword) {
		List<Post> posts = this.postRepository.searchByTitle("%"+keyword+"%");
		return posts.stream().map(postMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

}
