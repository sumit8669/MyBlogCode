package com.sumit.blog1.services;

import java.util.List;

import com.sumit.blog1.models.Post;
import com.sumit.blog1.payloads.PostDto;
import com.sumit.blog1.payloads.PostResponse;

public interface PostService {
	
	//Create
	
	PostDto createPost(PostDto postDto, Integer userId , Integer categoryId );
	
	//Update
	
	PostDto updatePost(PostDto postDto , Integer postId);

	
	//Delete
	
	void deletePost(Integer postId);
	
	//Get Single Post
	
	PostDto getPostById(Integer postId);
	
	//Get All Post
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	
	//Get Post by Category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	
	
	//Get post by User
	
	List<PostDto> getPostByUser(Integer userId);
	
	
	//Search By post
	
	List<PostDto> searchPosts(String keyword);
	
	
	
}
