package com.backend.blog.mapper;

import org.mapstruct.Mapper;

import com.backend.blog.dto.UserDTO;
import com.backend.blog.entities.User;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User>{
	default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}