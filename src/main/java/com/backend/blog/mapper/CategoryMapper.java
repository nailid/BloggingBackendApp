package com.backend.blog.mapper;

import org.mapstruct.Mapper;

import com.backend.blog.dto.CategoryDTO;
import com.backend.blog.entities.Category;
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
	default Category categoryFromId(Long id) {
		if (id==null) {
			return null;
		}
		Category category = new Category();
		category.setId(id);
		return category;
	}
}
