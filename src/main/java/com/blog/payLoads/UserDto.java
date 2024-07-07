package com.blog.payLoads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(min = 3, message ="Username lengnth must be greater than or equlas to 3")
	private String name;
	
	@Email(message="Email address is not valid !")
	private String email;
	@NotEmpty
	private String about;
	@NotEmpty
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$",
    message = "password must be min 4 and max 12 length containing atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String password;
}
