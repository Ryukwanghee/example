package com.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sample.dto.PostCommentListDto;
import com.sample.vo.Comment;

@Mapper
public interface PostCommentMapper {

	List<PostCommentListDto> getPostCommentsByPostNo(int postNo);

	void insertPostComment(Comment comment);
	
}
