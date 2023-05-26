package com.backend.blog.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backend.blog.dto.CategoryDTO;
import com.backend.blog.entities.Category;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.mapper.CategoryMapper;
import com.backend.blog.repositories.CategoryRepository;
import com.backend.blog.services.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		log.debug("Request to save Category : {}", categoryDTO);
		Category category = categoryMapper.toEntity(categoryDTO);
		category = this.categoryRepository.save(category);
		return categoryMapper.toDto(category);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long catId) {
		log.debug("Request to update Category : {}", categoryDTO, catId);
		Category category = this.categoryRepository.findById(catId).orElseThrow(()->
		new ResourceNotFoundException("Category ", "Category Id", catId));
		category.setTitle(categoryDTO.getTitle());
		category.setDescription(categoryDTO.getDescription());
		Category updatedCategory = this.categoryRepository.save(category);
		CategoryDTO result = categoryMapper.toDto(updatedCategory);
		return result;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		log.debug("Request to get all Categories");
		return categoryRepository.findAll().stream().map(categoryMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public void delete(Long catId) {
		log.debug("Request to delete Category : {}", catId);
		Category category = this.categoryRepository.findById(catId).orElseThrow(()->
		new ResourceNotFoundException("Category ", "Category Id", catId));	
		this.categoryRepository.deleteById(catId);
	}

	@Override
	public Optional<CategoryDTO> findOne(Long catId) {
		log.debug("Request to get Category : {}", catId);
		Category category = this.categoryRepository.findById(catId).orElseThrow(()->
		new ResourceNotFoundException("Category ", "Category Id", catId));
		return Optional.ofNullable(this.categoryMapper.toDto(category));
	}
}
