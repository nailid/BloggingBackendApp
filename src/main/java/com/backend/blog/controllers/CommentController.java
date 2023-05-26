package com.backend.blog.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.dto.CommentDTO;
import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.services.CommentService;

@RestController
@RequestMapping("/api/blog")
public class CommentController {
	private final Logger log = LoggerFactory.getLogger(CategoryController.class);
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	// api for add a new Comment
	@PostMapping("/post/{postId}/{userId}/comments")
	public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO, @PathVariable Long postId
			, @PathVariable Long userId) {
		log.debug("REST request to add Comment : {}", commentDTO);
		CommentDTO addedComment = this.commentService.addComment(commentDTO, postId, userId);
		return new ResponseEntity<CommentDTO>(addedComment, HttpStatus.CREATED);
	}

	@PutMapping("/post/{postId}/{userId}/comments")
	public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO, 
			@PathVariable Long postId, @PathVariable Long userId)
			throws Exception {
		log.debug("REST request to update Comment : {}", commentDTO);
		if (commentDTO.getId() == null) {
			throw new Exception("comment id not found !");
		}
		CommentDTO addedComment = this.commentService.addComment(commentDTO, postId, userId);
		return new ResponseEntity<CommentDTO>(addedComment, HttpStatus.CREATED);
	}

	// api for get all Comments
	@GetMapping("/comments")
	public List<CommentDTO> getAllComments() {
		log.debug("REST request to get all Comments");
		return commentService.getAllComments();
	}

	// api for get a Comments by id
	@GetMapping("/comments/{id}")
	public Optional<CommentDTO> getComment(@PathVariable("id") Long id) {
		log.debug("REST request to get Comment : {}", id);
		Optional<CommentDTO> commentsDTO = commentService.findOne(id);
		return commentsDTO;
	}

	// api for delete a Comment by id
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
		log.debug("REST request to delete Comment : {}", commentId);
		this.commentService.delete(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully !!", true), HttpStatus.OK);
	}
}
