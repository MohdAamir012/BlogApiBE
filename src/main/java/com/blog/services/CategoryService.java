package com.blog.services;

import java.util.List;

import com.blog.payLoads.CategoryDto;

public interface CategoryService {

//	create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
//	update category
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
//	Delete category
	void deleteCategory(Integer categoryId);
	
//	Get category by ID 
	CategoryDto getCategoryById(Integer categoryId);
	
//	Get all category
	List<CategoryDto> getAllCategories();
	
	
}
