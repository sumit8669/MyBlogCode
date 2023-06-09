package com.sumit.blog1.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sumit.blog1.exceptions.ResourceNotFoundException;
import com.sumit.blog1.models.Category;
import com.sumit.blog1.models.Post;
import com.sumit.blog1.models.User;
import com.sumit.blog1.payloads.PostDto;
import com.sumit.blog1.payloads.PostResponse;
import com.sumit.blog1.repository.CategoryRepo;
import com.sumit.blog1.repository.PostRepo;
import com.sumit.blog1.repository.UserRepo;
import com.sumit.blog1.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId , Integer categoryId ) {
		User user = this.userRepo.findById(userId)
								.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		
		Category category = this.categoryRepo.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
		 .orElseThrow(() -> new ResourceNotFoundException("Post","postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost , PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		this.postRepo.findById(postId)
					 .orElseThrow(() -> new ResourceNotFoundException("Post","postId", postId));
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
					 .orElseThrow(() -> new ResourceNotFoundException("Post", "PostID", postId));	
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		//Adding Pagination	and Sorting
		Sort sort  = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():Sort.by(sortBy).descending() ;
	
		
		Pageable p = PageRequest.of(pageNumber, pageSize ,sort);;
		
	Page<Post> pagePost =	this.postRepo.findAll(p);
	
	List<Post> allposts =	pagePost.getContent();
	List<PostDto> postDto =	allposts.stream()
	           		   			.map((post)-> this.modelMapper
	           		   			.map(post, PostDto.class))
	           		   			.collect(Collectors.toList());
	
	PostResponse postResponse = new PostResponse();
	
	postResponse.setContent(postDto);
	postResponse.setPageNumber(pagePost.getNumber());
	postResponse.setPageSize(pagePost.getSize());
	postResponse.setTotalElements(pagePost.getTotalElements());
	
	postResponse.setTotalPages(pagePost.getTotalPages());
	postResponse.setLastPage(pagePost.isLast());
	
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
	
		Category cat = this.categoryRepo
						   .findById(categoryId)
						   .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		 
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos = posts.stream()
							.map((post) -> this.modelMapper
							.map(post, PostDto.class))
							.collect(Collectors.toList());
		 
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo
						.findById(userId)
						.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream()
									.map((post) -> this.modelMapper
									.map(post, PostDto.class))
									.collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream()
									  .map((post) -> this.modelMapper.map(post, PostDto.class))
									  .collect(Collectors.toList());
		return postDtos;
	}
	
}
