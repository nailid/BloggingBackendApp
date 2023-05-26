package com.backend.blog.controllers;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.blog.dto.PostDTO;
import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.PostResponse;
import com.backend.blog.services.FileService;
import com.backend.blog.services.PostService;

@RestController
@RequestMapping("/api/blog")
public class PostController {
	private final Logger log = LoggerFactory.getLogger(PostController.class);
	private final PostService postService;
	private final FileService fileService;
	@Value("${project.image}")
	private String pathString;

	public PostController(PostService postService, FileService fileService) {
		super();
		this.postService = postService;
		this.fileService = fileService;
	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadImage(
			@PathVariable Long postId,
			@RequestParam("image") MultipartFile image) throws IOException{
		String fileName = this.fileService.uploadImage(pathString, image);
		PostDTO postDTO = this.postService.findOne(postId);
		postDTO.setImageName(fileName);
		PostDTO updatePost = this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "post/image/download/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)//produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(pathString, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	// api for creating a new Post
	@PostMapping("/post")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) throws Exception {
		log.debug("REST request to save Post : {}", postDTO);
		if (postDTO.getId() != null) {
			throw new Exception("post id already exists !");
		}
		PostDTO result = postService.addPost(postDTO);
		return ResponseEntity.ok().body(result);
	}

	// api for creating a new Post
	@PutMapping("/post")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO) throws Exception {
		log.debug("REST request to update Post : {}", postDTO);
		if (postDTO.getId() == null) {
			throw new Exception("post id already exists !");
		}
		PostDTO result = postService.addPost(postDTO);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/post/{id}")
	public ResponseEntity<PostDTO> updatePostById(@Valid @RequestBody PostDTO postDTO, @PathVariable("id") Long id) {
		log.debug("REST request to update Post by id : {}", postDTO);
		PostDTO updatedPost = this.postService.updatePost(postDTO, id);
		return ResponseEntity.ok(updatedPost);
	}

	// api for get all Post by pagination and sorting 
	@GetMapping("/post/page")
	public ResponseEntity<PostResponse> getAllPostByPagingSorting
	(@RequestParam(value = "pageNumber", defaultValue = "0",required = false) Integer pageNumber,
		@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
		@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
		@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) throws Exception {
		log.debug("REST request to get all Post with pagination");
		PostResponse postResponse = this.postService.getAllPostByPagingSorting(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	//search post by title
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPostsByTitle(@PathVariable("keyword")
	String keyword){
		List<PostDTO> result = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
	}
	
	// api for get all Post
	@GetMapping("/post")
	public ResponseEntity<List<PostDTO>> getAllPost(){
		log.debug("REST request to get all Post");
		List<PostDTO> postDTOs = this.postService.getAllPost();
		return new ResponseEntity<List<PostDTO>>(postDTOs, HttpStatus.OK);
	}
	
	// api for get a Category by id
	@GetMapping("/post/{id}")
	public PostDTO getPost(@PathVariable("id") Long id) {
		log.debug("REST request to get Post : {}", id);
		PostDTO postDTO = postService.findOne(id);
		return postDTO;
	}

	// api for delete a Category by id
	@DeleteMapping("/post/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("id") Long id) {
		log.debug("REST request to delete Post : {}", id);
		this.postService.delete(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted Successfully !", true), HttpStatus.OK);
	}
	
	//get List of post by giving user id
	@GetMapping("/post/user/{userId}")
	public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable("userId") Long userId){
		List<PostDTO> postByUser = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(postByUser, HttpStatus.OK);
	}
	
	//get List of post by giving category id
	@GetMapping("/post/category/{catId}")
	public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable("catId") Long catId){
		List<PostDTO> post = this.postService.getPostsByCategory(catId);
		return new ResponseEntity<List<PostDTO>>(post, HttpStatus.OK);
	}
}
