package com.sumit.blog1.services;

import java.util.List;

import com.sumit.blog1.payloads.CategoryDto;

public interface CategoryService {
	
	// create 
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId );
	
	
	//delete
	void deleteCategory(Integer categoryId );
	
	
	//get
	CategoryDto getCategory(Integer categoryId );
	
	//getAll
	List<CategoryDto> getCatagories();

}
