package com.backend.blog.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.backend.blog.dto.CategoryDTO;
import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.services.CategoryService;

@RestController
@RequestMapping("/api/blog")
public class CategoryController {
	private final Logger log = LoggerFactory.getLogger(CategoryController.class);
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	// api for creating a new Category
	@PostMapping("/category")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws Exception {
		log.debug("REST request to save Category : {}", categoryDTO);
		if (categoryDTO.getId() != null) {
			throw new Exception("category id already exists !");
		}
		CategoryDTO result = categoryService.createCategory(categoryDTO);
		return ResponseEntity.ok().body(result);
	}

	// api for update Category
	@PutMapping("/category")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws Exception {
		log.debug("REST request to update Category : {}", categoryDTO);
		if (categoryDTO.getId() == null) {
			throw new Exception("category id not found !");
		}
		CategoryDTO result = categoryService.createCategory(categoryDTO);
		return ResponseEntity.ok().body(result);
	}

	// api for update Category by id
	@PutMapping("/category/{id}")
	public ResponseEntity<CategoryDTO> updateCategoryById(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable("id") Long id) {
		log.debug("REST request to update Category by id : {}", categoryDTO);
		CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO, id);
		return ResponseEntity.ok(updatedCategory);
	}

	// api for get all Categories
	@GetMapping("/category")
	public List<CategoryDTO> getAllCategory() {
		log.debug("REST request to get all Categories");
		return categoryService.getAllCategories();
	}

	// api for get a Category by id
	@GetMapping("/category/{id}")
	public Optional<CategoryDTO> getCategory(@PathVariable("id") Long id) {
		log.debug("REST request to get Category : {}", id);
		Optional<CategoryDTO> categoryDto = categoryService.findOne(id);
		return categoryDto;
	}

	// api for delete a Category by id
	@DeleteMapping("/category/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Long id) {
		log.debug("REST request to delete Category : {}", id);
		this.categoryService.delete(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully !", true), HttpStatus.OK);
	}
}
