package com.blog.payLoads;

import java.util.Date;

import com.blog.entities.Category;
import com.blog.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer id;
	private String postTitle;
	private String postDescription;
	private String imageUrl;
	private Date addedAdd;
	private CategoryDto category;
	private UserDto user;
}
