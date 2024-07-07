package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstant;
import com.blog.payLoads.ApiResponse;
import com.blog.payLoads.PostDto;
import com.blog.payLoads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path; 

//	create 
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

//	update 
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.CREATED);
	}

//	Get post by user 
	@GetMapping("user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
			@PathVariable Integer userId) {
		PostResponse postDtos = this.postService.getAllPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postDtos, HttpStatus.OK);
	}

//	Get post by category 
	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
			@PathVariable Integer categoryId) {
		PostResponse postDtos = this.postService.getAllPostsByCategory(categoryId, pageNumber, pageSize, sortBy,
				sortDir);
		return new ResponseEntity<PostResponse>(postDtos, HttpStatus.OK);
	}

//	Get Post By ID
	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

//Get All Post 
	@GetMapping("posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "addedAdd", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PostResponse postDtos = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<PostResponse>(postDtos, HttpStatus.OK);
	}

//delete Post By ID
	@DeleteMapping("posts/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}

//Search 
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keywords") String keywords) {
		List<PostDto> results = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(results, HttpStatus.OK);
	}

//	Images controller
//	upload image
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		String uploadImage = this.fileService.uploadImage(path, image);
		
		postDto.setImageUrl(uploadImage);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		
		 return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
//	get Image 
	
	@GetMapping(value="/post/image/{postId}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable Integer postId,
	HttpServletResponse response
	) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		InputStream resource = this.fileService.getResource(path,postDto.getImageUrl());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	StreamUtils.copy(resource,response.getOutputStream());
	}
	
}
