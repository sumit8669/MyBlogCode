package com.sumit.blog1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4 , message = "UserName must be min of 4 Characters")
	private String name;
	
	@NotEmpty
	@Email(message = "Email address is not valid !!!")
	private String email;
	
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",
    message = "username must be of 6 to 12 length with no special characters")
	private String password;
	
	@NotEmpty
	private String about;
	
	
}
