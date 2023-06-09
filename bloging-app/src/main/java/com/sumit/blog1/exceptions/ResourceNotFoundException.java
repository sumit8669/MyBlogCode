package com.sumit.blog1.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{
	
	String resourceName;
	String fielName;
	long fieldValue;
	
	
	public ResourceNotFoundException(String resourceName, String fielName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName ,fielName,fieldValue));
		this.resourceName = resourceName;
		this.fielName = fielName;
		this.fieldValue = fieldValue;
	}
	
	

}
