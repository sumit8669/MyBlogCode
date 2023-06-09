package com.sumit.blog1.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumit.blog1.exceptions.ResourceNotFoundException;
import com.sumit.blog1.models.Category;
import com.sumit.blog1.payloads.CategoryDto;
import com.sumit.blog1.repository.CategoryRepo;
import com.sumit.blog1.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.mapper.map(categoryDto, Category.class);
		Category addedCat=this.categoryRepo.save(cat);
		return this.mapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCat = this.categoryRepo.save(cat);
		return this.mapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
		    .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return this.mapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCatagories() {
	List<Category> categories =	this.categoryRepo.findAll();
	List<CategoryDto> catDao = categories.stream()
			.map((cat) -> this.mapper.map(cat, CategoryDto.class))
			.collect(Collectors.toList());
		return catDao;
	}

}
