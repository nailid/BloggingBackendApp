package com.backend.blog.services;

import java.util.List;
import java.util.Optional;
import com.backend.blog.dto.CommentDTO;

public interface CommentService {
	//method for add a Comment
		CommentDTO addComment(CommentDTO commentDTO, Long postId, Long userId);
		//method for update a Comment By Id
		CommentDTO updateComment(CommentDTO commentDTO, Long commentId);
		//method for get all Comments
		List<CommentDTO> getAllComments();
		//method for delete a Comment By Id
		void delete(Long id);
		//method for get a Comment By Id
		Optional<CommentDTO> findOne(Long commentId);
}
