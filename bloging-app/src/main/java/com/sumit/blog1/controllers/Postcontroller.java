package com.sumit.blog1.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sumit.blog1.config.AppConstants;
import com.sumit.blog1.payloads.ApiResponse;
import com.sumit.blog1.payloads.PostDto;
import com.sumit.blog1.payloads.PostResponse;
import com.sumit.blog1.services.FileService;
import com.sumit.blog1.services.PostService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class Postcontroller {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@org.springframework.beans.factory.annotation.Value("${project.image}")
	private String path;
	
	//Create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createpost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			)
	{
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//Get By Users
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts , HttpStatus.OK);
	}
	
	//Get By Category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostBycategory(@PathVariable Integer categoryId)
	{
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts , HttpStatus.OK);
	}
	
	//Get All posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY, required = false)String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR , required = false)String sortDir
			){
		PostResponse postResponse = this.postService.getAllPost( pageNumber,pageSize,sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	
	//Get Single post
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
			PostDto postDto = this.postService.getPostById(postId);
			return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
		}
	 
	//Delete Post
		@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePost(@PathVariable Integer postId) {
			this.postService.deletePost(postId);
		 return	new ApiResponse("Post is successfully deleted !!",true);
		}
		
	//Update Post
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost (@RequestBody PostDto postDto, @PathVariable Integer postId){
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatePost ,HttpStatus.OK);
		}
		
		
	//Search	
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keywords){
			List<PostDto> result = this.postService.searchPosts(keywords);
			return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
		}
		
	//Post Image upload	
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId
				) throws IOException{
			
			PostDto postDto = this.postService.getPostById(postId);
			
			String fileName = this.fileService.uploadImage(path, image);
			
			postDto.setImageName(fileName);
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatePost ,HttpStatus.OK);
		}
		
	//Method to serve files
		@GetMapping(value = "/post/image/{imageName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageName") String imageName,
				HttpServletResponse response
				)throws IOException {
			InputStream resource  = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
			
		}
		
		
		
		
		
	
}
