package com.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sample.dto.PostListDto;
import com.sample.vo.Post;


@Mapper
public interface PostMapper {

	List<PostListDto> getPosts();
	void insertPost(Post post);
	void updatePost(Post post);
	Post getPostByNo(int postNo);
}
