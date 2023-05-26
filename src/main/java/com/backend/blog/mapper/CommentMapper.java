package com.backend.blog.mapper;

import org.mapstruct.Mapper;

import com.backend.blog.dto.CommentDTO;
import com.backend.blog.entities.Comment;

@Mapper(componentModel = "spring", uses = {})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
	default Comment commentFromId(Long id) {
		if (id==null) {
			return null;
		}
		Comment comment = new Comment();
		comment.setId(id);
		return comment;
	}
}
