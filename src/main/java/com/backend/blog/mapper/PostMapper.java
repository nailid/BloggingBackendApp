package com.backend.blog.mapper;

import org.mapstruct.Mapper;

import com.backend.blog.dto.PostDTO;
import com.backend.blog.entities.Post;

@Mapper(componentModel = "spring", uses = {})
public interface PostMapper extends EntityMapper<PostDTO, Post>{
	default Post postFromId(Long id) {
		if (id==null) {
			return null;
		}
		Post post = new Post();
		post.setId(id);
		return post;
	}
}
