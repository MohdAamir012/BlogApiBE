package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.entities.Category;
import com.blog.payLoads.ApiResponse;
import com.blog.payLoads.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
//	create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createdCatDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>( createdCatDto,HttpStatus.CREATED);
		
	}
	
	
//	update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer catId){
		
		CategoryDto updatedCatDto = this.categoryService.updateCategory(categoryDto,catId);
		return new ResponseEntity<CategoryDto>( updatedCatDto,HttpStatus.OK);
		
	}
//	delete
	@DeleteMapping("/{categoryid}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryid){
			this.categoryService.deleteCategory(categoryid);		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully",true),HttpStatus.OK);
		
	}
//	getCategoryById
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer catId){
		CategoryDto catDto= this.categoryService.getCategoryById(catId);		
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.OK);
	}
	
//	getAllCategory
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> categories= this.categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	
	
}
