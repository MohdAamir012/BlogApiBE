package com.blog.services;

import java.util.Date;
import java.util.List;

import com.blog.entities.Post;
import com.blog.payLoads.PostDto;
import com.blog.payLoads.PostResponse;

public interface PostService {

//	create Post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

//	update Post
	PostDto updatePost(PostDto postDto,Integer postId);
	
//	delete post
	void deletePost(Integer postId);
	
//	getAll Posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	
//	getPostByID
	PostDto getPostById(Integer postId);
	
//	get post of category
	
	PostResponse getAllPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	
//	get post by user 
	
	PostResponse getAllPostByUser(Integer userId,Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	
//	get searched Post
	
	List <PostDto> searchPosts(String keywords);
	
	
}
