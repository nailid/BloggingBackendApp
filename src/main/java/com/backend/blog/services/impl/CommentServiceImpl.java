package com.backend.blog.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backend.blog.dto.CommentDTO;
import com.backend.blog.entities.Comment;
import com.backend.blog.entities.Post;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.mapper.CommentMapper;
import com.backend.blog.repositories.CommentRepository;
import com.backend.blog.repositories.PostRepository;
import com.backend.blog.repositories.UserRepository;
import com.backend.blog.services.CommentService;

@Service
@Transactional 
public class CommentServiceImpl implements CommentService{
	private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final CommentMapper commentMapper;

	public CommentServiceImpl(PostRepository postRepository, UserRepository userRepository,
			CommentMapper commentMapper, CommentRepository commentRepository) {
		super();
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.commentMapper = commentMapper;
		this.userRepository = userRepository;
	}

	@Override
	public CommentDTO addComment(CommentDTO commentDTO, Long postId, Long userId) {
		log.debug("Request to add Comment : {}", commentDTO);
		Post post = this.postRepository.findById(postId).orElseThrow(()->new 
				ResourceNotFoundException("Post ", "Post Id", postId));
		User user = this.userRepository.findById(userId).orElseThrow(()->new
				ResourceNotFoundException("User ", "User Id", userId)); 
		Comment comment = this.commentMapper.toEntity(commentDTO);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.commentRepository.save(comment);
		return this.commentMapper.toDto(savedComment);
	}

	@Override
	public CommentDTO updateComment(CommentDTO commentDTO, Long commentId) {
		log.debug("Request to update Comment : {}", commentDTO);
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->
		new ResourceNotFoundException("Comment", "commentId", commentId));
		comment.setContent(commentDTO.getContent());
		Comment updatedComment = this.commentRepository.save(comment);
		CommentDTO result = commentMapper.toDto(updatedComment);
		return result;
	}

	@Override
	public List<CommentDTO> getAllComments() {
		log.debug("Request to get all Comments");
		return commentRepository.findAll().stream().map(commentMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Comment : {}", id);
		Comment comment = this.commentRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Comment", "commentId", id));
		this.commentRepository.deleteById(id);
	}

	@Override
	public Optional<CommentDTO> findOne(Long commentId) {
		log.debug("Request to get Comment : {}", commentId);
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->
		new ResourceNotFoundException("Comment", "commentId", commentId));
		return Optional.ofNullable(commentMapper.toDto(comment));
	}
}
