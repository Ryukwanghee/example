package com.sample.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.dto.PostCommentListDto;
import com.sample.dto.PostDetailDto;
import com.sample.dto.PostListDto;
import com.sample.exception.ApplicationException;
import com.sample.mapper.PostCommentMapper;
import com.sample.mapper.PostMapper;
import com.sample.utils.Pagination;
import com.sample.vo.AttachedFile;
import com.sample.vo.Comment;
import com.sample.vo.Post;
import com.sample.vo.Tag;
import com.sample.web.request.PostModifyForm;
import com.sample.web.request.PostRegisterForm;

@Service
@Transactional	//트랜잭션은 오류 발생시 롤백, 아닐때는 커밋 논리적작업단위
public class PostService {
	
	// PostService는 매퍼 2개 주입받는다.
	@Autowired
	private PostCommentMapper postCommentMapper;
	
	@Autowired
	private PostMapper postMapper; // 서비스는 매퍼가 필요
	
	// 게시글 등록서비스
	public void insertPost(String userId, PostRegisterForm form) {
		// SPRING_POSTS 테이블에 게시글 정보 저장
		Post post = new Post();	// 게시글 등록
		post.setUserId(userId);
		// BeanUtils.copyProperties(form, post);	form과 post의 같은 이름에 대한 값을 form->post로 복사해줌
		post.setTitle(form.getTitle());
		post.setContent(form.getContent());
		
		postMapper.insertPost(post);
		
		// SPRING_POST_ATTACHED_FILES 테이블에 게시글 첨부파일 정보 저장
		if (form.getFilename() != null) {	// 파일이름으로 파일있는지 확인, 있으면 파일등록
		AttachedFile attachedFile = new AttachedFile();
		attachedFile.setPostNo(post.getNo());	//post.xml에 selectKey를 이용해서 사용했다. 부모테이블에서 자식테이블 자식테이블 자식테이블 이런식으로 생성되고 저장될 때 no를 더 편리하게 사용할 수 있다.
		attachedFile.setFilename(form.getFilename());
		
		postMapper.insertAttachedFile(attachedFile);
		
		}
		// SPRING_POST_TAGS 테이블에 게시글 태그정보 저장
		if (form.getTags() != null) {
			List<String> tags = form.getTags();
			for (String tagContent : tags) {
				Tag tag = new Tag(post.getNo(), tagContent);	// 셀렉트 키에서 no가 전달
				postMapper.insertTag(tag);
			}
		}
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
		//게시글 상세정보 조회
		PostDetailDto postDetaildto = postMapper.getPostDetailByNo(postNo);	
		if (postDetaildto == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		
		// 댓글정보조회
		List<PostCommentListDto> postCommentListDtos = postCommentMapper.getPostCommentsByPostNo(postNo); //게시글 댓글 조회
		postDetaildto.setComments(postCommentListDtos);
		
		// 첨부파일 정보 조회
		List<AttachedFile> attachedFiles = postMapper.getAttachedFilesByPostNo(postNo);
		postDetaildto.setAttachedFiles(attachedFiles);
		
		// 태그 정보 조회
		List<Tag> tags = postMapper.getTagsByPostNo(postNo);
		postDetaildto.setTags(tags);
		
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

	public void updatePost(PostModifyForm postModifyForm) {
		Post post = new Post();
		BeanUtils.copyProperties(postModifyForm, post);
		
		postMapper.updatePost(post);
	}
}
