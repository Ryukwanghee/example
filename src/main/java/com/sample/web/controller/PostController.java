package com.sample.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.dto.PostDetailDto;
import com.sample.dto.PostListDto;
import com.sample.service.PostService;
import com.sample.web.login.LoginUser;
import com.sample.web.login.LoginUserInfo;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService postService;

	@GetMapping("/list")
	public String list(Model model) {
		List<PostListDto> posts = postService.getAllPosts();
		model.addAttribute("posts", posts);
		
		return "post/list";
	}
	
	@GetMapping("/insert")
	@LoginUser
	public String form() {
		return "post/form";
	}
	
	@PostMapping("/insert")
	public String addPost(@LoginUser LoginUserInfo loginUserInfo,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content) {
		
		postService.addPost(loginUserInfo.getId(), title, content);
		
		return "redirect:list";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("postNo")int postNo) {
		postService.increaseReadCount(postNo);
		
		return "redirect:detail?postNo=" + postNo;
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam("postNo")int postNo, Model model) {
		PostDetailDto dto = postService.getPostDetail(postNo);
		model.addAttribute("post", dto);
		
		return "post/detail";
	}
}
