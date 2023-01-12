package com.sample.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sample.dto.PostDetailDto;
import com.sample.dto.PostListDto;
import com.sample.vo.AttachedFile;
import com.sample.vo.Post;


@Mapper
public interface PostMapper {

	void insertPost(Post post); // @Param("id") String userId, @Param("post") Post post @Param을 쓰면 값을 여러 개 전달할 수 있다. xml에서는 parameterType을 뺴야함
								// userId에서 파람이름을"id"로 주면 xml에서 id 는 #{id}로 써야함 #{post.title} #{post.content}
	void updatePost(Post post);
	Post getPostByNo(int postNo);
	
	int getTotalRows();
	List<PostListDto> getPosts(Map<String, Object> param);
	
	PostDetailDto getPostDetailByNo(int postNo);
	
	void insertAttachedFile(AttachedFile attachedFile);
}
