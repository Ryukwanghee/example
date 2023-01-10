package com.sample.service;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dto.PostDetailDto;
import com.sample.dto.PostListDto;
import com.sample.mapper.PostMapper;
import com.sample.vo.Post;

@Service
public class PostService {
	
	@Autowired
	private PostMapper postMapper;
	
	public List<PostListDto> getAllPosts() {
		return postMapper.getPosts();
	}
	
	public void addPost(String userId, String title, String content) {
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(title);
		post.setContent(content);
		
		postMapper.insertPost(post);
	}
	
	public void increaseReadCount(int postNo) {
		Post post = new Post();
		post.setReadCount(post.getReadCount() + 1);
		
		postMapper.updatePost(post);
	}
	
	public PostDetailDto getPostDetail(int postNo) {
		Post post = postMapper.getPostByNo(postNo);
		
		PostDetailDto postDetailDto = new PostDetailDto();
		
		BeanUtils.copyProperties(post, postDetailDto);
		
		return postDetailDto;
	}
}
