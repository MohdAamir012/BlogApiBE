package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payLoads.PostDto;
import com.blog.payLoads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo; 
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->
			new ResourceNotFoundException("User", "User ID", userId));	
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->
			new ResourceNotFoundException("Category", "Category ID", categoryId));
		
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageUrl("default.png");
		post.setAddedAdd(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepo.save(post);
		// TODO Auto-generated method stub
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto,Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "post Id", postId));
		
		post.setPostTitle(postDto.getPostTitle());
		post.setPostDescription(postDto.getPostDescription());
		post.setImageUrl(postDto.getImageUrl());
		
		Post updatedPost = this.postRepo.save(post);
		
		PostDto updatedPostDto = this.modelMapper.map(updatedPost, PostDto.class);
		return updatedPostDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "post Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pagePosts = this.postRepo.findAll(p);
		List<Post> posts=pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setIsLast(pagePosts.isLast());
		
		return postResponse;
		}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "Post ID ", postId)); 
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		// TODO Auto-generated method stub
		return postDto;
	}

	@Override
	public PostResponse getAllPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->
				new ResourceNotFoundException("Category ", "Category ID ", categoryId));
	
		Page<Post> pagePosts = this.postRepo.findByCategory(cat,p);
		List<Post> posts = pagePosts.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setIsLast(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getAllPostByUser(Integer userId,Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "User ID ", userId));
		Page<Post> pagePosts = this.postRepo.findByUser(user,p);
		List<Post> posts = pagePosts.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setIsLast(pagePosts.isLast());
		
		
		return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keywords) {
		List <Post> posts= this.postRepo.findByPostTitleContaining(keywords);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	

}
