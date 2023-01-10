package com.sample.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.dto.UserDetailDto;
import com.sample.service.UserService;
import com.sample.utils.SessionUtils;
import com.sample.web.login.LoginUser;
import com.sample.web.login.LoginUserInfo;

@Controller
@RequestMapping("/user")	// 여기에 /user랑 밑에 url요청이랑 합쳐지는 것임 ex) /user/info
public class UserController {

	@Autowired //의존성 주입을 받는 어노테이션
	private UserService userService;
	
	@GetMapping("/info") // /info로 url 요청하면 아래를 실행
	public String info(@LoginUser LoginUserInfo loginUserInfo, Model model) {	//로그인한 사용자 정보가 필요하니까 @LoginUser LoginUserInfo loginUserInfo
		// 사용자 정보를 담고 사용해야하니까 request.setAttribute와 같은 Model model 사용
		
		UserDetailDto userDetailDto = userService.getUserDetail(loginUserInfo.getId()); // 전해줄때는 Model model 을사용
		model.addAttribute("user", userDetailDto); //request.setAttribute 와 비슷한 기능을 model이 한다. user의 이름으로 detail.jsp에서 불러쓴다.
		
		return "user/detail";
	}
	
	@GetMapping("/delete")
	@LoginUser
	public String deleteform() {
		return "user/delete-form";	// 회원탈퇴화면의 이름을 반환한다. /WEB-INF/views/user/delete-form.jsp
	}
	
	@PostMapping("/delete")
	public String delete(@LoginUser LoginUserInfo loginUserInfo, String password) { //LoginUserInfo loginUserInfo이걸통해서 로그인된 사용자 정보를 가져옴
		// 탈퇴처리 업무로직을 호출한다.
		userService.deleteUser(loginUserInfo.getId(), password);
		// 세션에 저장된 로그인정보를 삭제한다.
		SessionUtils.removeAttribute("loginUser");
		
		return "redirect:delete-success";	// 회원탈퇴 성공화면을 재요청하는 URL을 반환한다. 탈퇴성공하면 password 값 받음, 재요청하면 view/user/까지 살아있으니까 delete-success
	}
	
	@GetMapping("/delete-success")
	@LoginUser	//로그인이 필요한 곳에 붙임, 메소드나 파라미터에 로그인이 없는 곳에.
	public String deleteSuccess() {
		return "user/delete-success";	// 회원탈퇴성공화면의 이름을 반환한다. /WEB-INF/views/user/delete-success.jsp
	}
	
	@GetMapping("/password")
	@LoginUser
	public String passwordChangeForm() {
		return "user/password-form";
	}
	
	// 메소드나 파라미터에 LoginUser가 있는지 확인, 없으면 로그인 여부를 체크하는 곳에 @LoginUser 적음, LoginUser.java에 메소드랑 파라미터를 정의해뒀다.
	
	@PostMapping("/password")
	public String changePassword(@LoginUser LoginUserInfo loginUserInfo,
			@RequestParam(name = "oldPassword") String oldPassword,
			@RequestParam(name = "password") String password) {
		
		userService.changePassword(loginUserInfo.getId(), oldPassword, password);
		
		return "redirect:password-success";	// redirect에 / 붙이면 절대경로가 돼서 안된다. / 빼고 상대경로로 입력해줘. /하면 localhost/password-success가 되어버려
	}
	
	@GetMapping("/password-success")
	public String passwordChangeSuccess() {
		return "user/password-success";
	}
}
