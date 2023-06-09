package com.sumit.blog1.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumit.blog1.exceptions.ResourceNotFoundException;
import com.sumit.blog1.models.User;
import com.sumit.blog1.payloads.UserDto;
import com.sumit.blog1.repository.UserRepo;
import com.sumit.blog1.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User saveUser = this.userRepo.save(user);
		
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateUser = this.userRepo.save(user);
		UserDto userDto1 =this.userToDto(updateUser);	
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllusers() {
	List<User> users =	this.userRepo.findAll();
	
		List<UserDto>userDtos = users.stream()
							.map(user -> this.userToDto(user))
							.collect(Collectors.toList());	
	
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
		   		.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId) );
		
	}
	
	// MAP USERDTO TO USER
	
	private User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto,User.class);
		
		
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
		
		return user;
	}
	
	// MAP  USER  TO USERDTO 
	
	private UserDto userToDto(User user) {
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
