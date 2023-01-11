package com.sample.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dto.PostCommentListDto;
import com.sample.dto.PostDetailDto;
import com.sample.dto.PostListDto;
import com.sample.exception.ApplicationException;
import com.sample.mapper.PostCommentMapper;
import com.sample.mapper.PostMapper;
import com.sample.utils.Pagination;
import com.sample.vo.Comment;
import com.sample.vo.Post;
import com.sample.web.request.PostRegisterForm;

@Service
public class PostService {
	
	// PostService는 매퍼 2개 주입받는다.
	@Autowired
	private PostCommentMapper postCommentMapper;
	
	@Autowired
	private PostMapper postMapper; // 서비스는 매퍼가 필요
	
	public void insertPost(String userId, PostRegisterForm form) {
		Post post = new Post();
		post.setUserId(userId);
		// BeanUtils.copyProperties(form, post);
		post.setTitle(form.getTitle());
		post.setContent(form.getContent());
		
		postMapper.insertPost(post);
	}
	
	// 게시글의 조회수를 증가시키는 서비스
	public void increaseReadCount(int postNo) { //컨트롤러에서 수행하면 하나의 트랜잭션으로 묶이지가 않아서 서비스에서 정의한다. 업무로직을 실행하는 메소드에 있어야한다.
		Post post = postMapper.getPostByNo(postNo);					// 컨트롤러는 클라이언트가 전해준 값을 서비스에게 전달해주는게 끝이기 때문에 서비스가 이런 작업을 수행. 컨트롤러는 가볍게 서비스는 무겁게
		if (post == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		if ("Y".equals(post.getDeleted())) {
			throw new ApplicationException("["+postNo+"] 번 게시글은 삭제된 게시글입니다.");
		}
		
		post.setReadCount(post.getReadCount() + 1);
		postMapper.updatePost(post);
	}
	
	// 게시글상세정보(게시글 정보와 댓글목록 정보)를 제공하는 서비스
	public PostDetailDto getPostDetail(int postNo) {
		PostDetailDto postDetaildto = postMapper.getPostDetailByNo(postNo);	//게시글 상세정보 조회
		if (postDetaildto == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		
		List<PostCommentListDto> postCommentListDtos = postCommentMapper.getPostCommentsByPostNo(postNo); //게시글 댓글 조회
		postDetaildto.setComments(postCommentListDtos);
		
		return postDetaildto;
	}

	public Map<String, Object> getPosts(int page) {
		int totalRows = postMapper.getTotalRows();
		Pagination pagination = new Pagination(page, totalRows);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("begin", pagination.getBegin());
		param.put("end", pagination.getEnd());
		
		List<PostListDto> posts = postMapper.getPosts(param);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("posts", posts);
		result.put("pagination", pagination);
		
		return result;
		
	}

	// 댓글을 등록하는 기능
	//public void insertComment(Comment comment) {
	public void insertComment(String userId, String content, int postNo) {
		// Post post = postMapper.getPostByNo(comment.getPostNo());
		Post post = postMapper.getPostByNo(postNo);
		if (post == null) {
			//throw new ApplicationException("["+comment.getPostNo()+"] 번 게시글이 존재하지 않습니다.");
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		
		Comment comment = new Comment(); // form안쓰는 거는 그냥 자율임 , 입력하는 항목이 많으면 대체로 씀
		comment.setUserId(userId);
		comment.setContent(content);
		comment.setPostNo(postNo);
		postCommentMapper.insertPostComment(comment);
		
		post.setCommentCount(post.getCommentCount() + 1);
		postMapper.updatePost(post);
		
	}
}
