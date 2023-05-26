package com.backend.blog.services;

import java.util.List;
import java.util.Optional;

import com.backend.blog.dto.CategoryDTO;
//A Service Interface
public interface CategoryService {
	//method for create a Category
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	//method for update a Category By Id
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Long catId);
	//method for get all categories
	List<CategoryDTO> getAllCategories();
	//method for delete a Category By Id
	void delete(Long id);
	//method for get a Category By Id
	Optional<CategoryDTO> findOne(Long catId);
}
