package com.blog.entities;

import java.util.Date;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="postTitle",length=100,nullable = false)
	private String postTitle;
	@Column(name="postDescription",length=1000,nullable = false)
	private String postDescription;
	
	private String imageUrl;
	private Date addedAdd;
	
	@ManyToOne
	@JoinColumn(name= "categoryId")
	private Category category;
	@ManyToOne
	@JoinColumn(name= "userId")
	private User user;
	
	
	
}
