package com.sample.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.dto.PostDetailDto;
import com.sample.service.PostService;
import com.sample.web.login.LoginUser;
import com.sample.web.login.LoginUserInfo;
import com.sample.web.request.PostRegisterForm;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService postService; // 컨트롤러는 서비스가 필요

	// 요청 URL - http://localhost/post/list
	//		  	 http://localhost/post/list?page=2
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", required = false, defaultValue = "1") int page, Model model) { //요청 파라미터에서 페이지란 이름으로 찾을거다 라는 말 페이지가 없을 때 1
			// required가 false로 작성해주면 null 이어도 에러발생 안함,  
		
		Map<String, Object> result = postService.getPosts(page);
		model.addAttribute("posts", result.get("posts"));	//모델에 각각 나눠서 담아준 것
		model.addAttribute("pagination", result.get("pagination"));	//모델에 각각 나눠서 담아준 것
		
		
		return "post/list";
	}
	
	@GetMapping("/insert")
	@LoginUser
	public String form() {
		return "post/form";
	}
	
	@PostMapping("/insert")
	public String insertPost(@LoginUser LoginUserInfo loginUserInfo, PostRegisterForm form) { //입력요소를 전달받을 클래스 만들어줌. PostRegisterForm
		// controller-> service->mapper	->service -> controller
		postService.insertPost(loginUserInfo.getId(), form);
		
		return "redirect:list";
	}
	
	// 요청 URL - http://localhost/post/read?postNo=2
	@GetMapping("/read") //조회수 증가
	public String read(@RequestParam("postNo") int postNo) { //postNo를 받는다
		postService.increaseReadCount(postNo);
		
		return "redirect:detail?postNo=" + postNo;	//redirect에서 detail을 호출하면서 postNo까지 전달
	}
	
	// 요청 URL - http://localhost/post/detail?postNo=2
	@GetMapping("/detail") // 상세정보
	public String detail(@RequestParam("postNo")int postNo, Model model) { //jsp에게 전해주려면 Model이 있어야한다. model에 담아줌
		PostDetailDto postDetailDto = postService.getPostDetail(postNo);
		
		model.addAttribute("post", postDetailDto);	//PostDetailDto
		// model.addAttribute("comments", postDetailDto.getComments()); //List<PostCommentListDto> 하나로 보내도 되고 이것까지 두개로 보내도 된다.
		
		return "post/detail";
	}
	
	@PostMapping("/insert-comment")
	public String insertComment(@LoginUser LoginUserInfo loginUserInfo,
			@RequestParam("postNo") int postNo,
			@RequestParam("content") String content) {	//detail에서 댓글쓰는 곳에 postNo라는 이름과 content라는 이름을 전달받음
		
		/* 두가지 방법이 있다. 지금쓴건 Controller를 가볍게 만든것
		 * Comment comment = new Comment(); // form안쓰는 거는 그냥 자율임 , 입력하는 항목이 많으면 대체로 씀
		 * comment.setUserId(loginUserInfo.getId()); comment.setContent(content);
		 * comment.setPostNo(postNo);
		 */
		 // postService.insertComment(comment);
		postService.insertComment(loginUserInfo.getId(), content, postNo);
		
		return "redirect:detail?postNo=" + postNo;
	}
}
