package com.blog.payLoads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryID;
	
	@NotBlank
	@Size(min=3, message="Minimum size of title is 3")
	private String categoryTitle;

	@NotBlank
	@Size(min=3, message="Minimum size of description is 3")
	private String categoryDescription;
}
