package com.sumit.blog1.services;

import java.util.List;

import com.sumit.blog1.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto); 
	
	UserDto updateUser(UserDto userDto, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllusers();
	
	void deleteUser(Integer userId);
	
	
}
