package com.sumit.blog1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumit.blog1.payloads.ApiResponse;
import com.sumit.blog1.payloads.UserDto;
import com.sumit.blog1.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	//POST - Create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto =this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//PUT - Update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser (@Valid @RequestBody UserDto userDto,@PathVariable("userId")Integer uid){
		UserDto updatedUser = this.userService.updateUser(userDto, uid);
	
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE - Delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")Integer uid){
		this.userService.deleteUser(uid);
		return new ResponseEntity<>( new ApiResponse("User Deleted SuccessFully",true),HttpStatus.OK);
	}
	
	//GET - Get user 
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity.ok(this.userService.getAllusers());
	}
	
	
	//GET - Get user 
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
			return ResponseEntity.ok(this.userService.getUserById(userId));
		}
		
}
