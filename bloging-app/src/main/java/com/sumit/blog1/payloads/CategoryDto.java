package com.sumit.blog1.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4 , message = "Title must be min of 4 Characters")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 10 , message = "Description must be min of 10 Characters")
	private String categoryDescription;

}
