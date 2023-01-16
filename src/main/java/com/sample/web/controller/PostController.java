package com.sample.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sample.dto.PostDetailDto;
import com.sample.exception.ApplicationException;
import com.sample.service.PostService;
import com.sample.web.login.LoginUser;
import com.sample.web.login.LoginUserInfo;
import com.sample.web.request.PostRegisterForm;
import com.sample.web.view.FileDownloadView;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private final String directory = "c:/files";
	
	// 스프링의 빈으로 등록되어있으니까 주입받을 수 있는 것
	@Autowired
	private FileDownloadView fileDownloadView;
	
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
	public String insertPost(@LoginUser LoginUserInfo loginUserInfo, PostRegisterForm form) throws IOException{ //입력요소를 전달받을 클래스 만들어줌. PostRegisterForm
		// 첨부파일 업로드 처리 (PostRegisterForm에 들어가 있으므로)
		MultipartFile upfile = form.getUpfile();
		if (!upfile.isEmpty()) {
			// 첨부파일이름을 조회하고, PostRegisterForm객체에 대입한다.
			String filename = upfile.getOriginalFilename(); // 반드시 이 이름으로 써줘야함. 자바에서 제공되는 것
			form.setFilename(filename);	//파일이름을 form에 담아둠 service에 주려고 form에 담는것
			
			// 첨부파일을 지정된 디렉토리에 저장한다. upfile과 중복되는 부분을 오른쪽 file에 복사한다.
			FileCopyUtils.copy(upfile.getInputStream(), new FileOutputStream(new File(directory, filename)));
		}
		
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
	
	@GetMapping("/download")
	public ModelAndView fileDownload(@RequestParam("filename") String filename) {
		// 지정된 파일정보를 표현하는 File객체 생성
		File file = new File(directory, filename);
		// 파일이 존재하지 않으면 예외를 던진다. 파일이 존재하지 않더라도 File객체는 생성할 수 있다. 파일이 존재하지 않는다해도 null은 아님 그래서 객체를 생성할 수 있는것.
		if (!file.exists()) {
			throw new ApplicationException("["+filename+"] 파일이 존재하지 않습니다.");
		}
		
		ModelAndView mav = new ModelAndView();
		
		// ModelAndView의 Model에 값 저장 뷰의모델
		mav.addObject("file", file); //파일 자체를 전해줌
		
		// ModelAndView의 View에 DownloadView 객체 저장
		mav.setView(fileDownloadView);
		
		return mav;
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
