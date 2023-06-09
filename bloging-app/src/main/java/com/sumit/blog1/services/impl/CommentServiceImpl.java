package com.sumit.blog1.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumit.blog1.exceptions.ResourceNotFoundException;
import com.sumit.blog1.models.Comment;
import com.sumit.blog1.models.Post;
import com.sumit.blog1.payloads.CommentDto;
import com.sumit.blog1.repository.CommentRepo;
import com.sumit.blog1.repository.PostRepo;
import com.sumit.blog1.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	 @Autowired
	 private CommentRepo commentRepo;
	 
	 @Autowired
	 private PostRepo postRepo;
	
	 @Autowired
	 private ModelMapper mapper;
	 
	 
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
			Post post = this.postRepo.findById(postId)
							.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		
			 Comment comment = this.mapper.map(commentDto, Comment.class);
			 comment.setPost(post);
			 
			 Comment saveComment = this.commentRepo.save(comment);
			
			return this.mapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId)
						.orElseThrow(() -> new ResourceNotFoundException("comment","commentId",commentId));
		
		this.commentRepo.delete(com);
	}

}
