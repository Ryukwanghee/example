package com.sample.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.service.UserService;
import com.sample.utils.SessionUtils;
import com.sample.vo.User;
import com.sample.web.login.LoginUserInfo;
import com.sample.web.request.UserRegisterForm;

@Controller
public class HomeController {
	
	@Autowired //스프링 컨테이너가 UserService에 주소값을 주입
	private UserService userService; //UserService에서 의존성 주입받음
	
	@GetMapping("/home")
	public String home() {
		
		/*
		 * public String home(Model model) { // 뷰에 전달한 데이터를 Model객체에 추가하기
		 * model.addAttribute("msg", "웹 페이지 방문을 환영합니다.");
		 */
		// home.jsp로 내부이동해라, 내부이동할 뷰페이지 이름을 반환
		return "home"; 
	}
	
	@GetMapping("/register") // /register url요청이 오면 register-form을 반환
	public String registerform( ) {
		return "register-form";
	}
	
	@PostMapping("/register")
	/*
	public String register(UserRegisterForm userRegisterForm, Model model) { //회원가입 등록 입력 값을 userRegisterForm에 저장
		
		try {
		userService.registerUser(userRegisterForm);
		} catch (RuntimeException e) {
			model.addAttribute("message", e.getMessage());
			return "register-form"; 
		}
		return "redirect:/success";
		*/
		
		public String register(UserRegisterForm userRegisterForm) { // 값이 많아서 폼을 새로 만든것
		/*
			try {
				userService.registerUser(userRegisterForm);
			} catch (RuntimeException e) {
				e.printStackTrace;
				return "redirect:register?error=fail"; 
			}
			return "redirect:/success";
		*/
		userService.registerUser(userRegisterForm);
		return "redirect:/success";
	}
	
	@GetMapping("/success") // GET 방식이냐 POST방식이냐에 대한 차이
	public String success() {
		return "success";
	}
	
	@GetMapping("/login") //    컨트롤러에 하나당 하나가 서비스에 존재, /login이 url경로
	public String loginform( ) { // 값이 2개밖에 없으니까 폼 새로 안만들고 그냥 사용.
		return "login-form";
	}
	
	/*
	 * 요청핸들러 메소드의 매개변수가 기본자료형 혹은 String형 인 경우
	 * 		매개변수의 이름과 동일한 이름으로 요청파라미터 값을 조회하여 매개변수로 전달한다.
	 * 		매개변수의 타입이 기본자료형 타입인 경우 해당 타입으로 형변환해서 전달한다.
	 * 		매개변수의 타입이 기본자료형(정수, 실수, 문자, 불린)일 때,
	 * 		요청파라미터값이 존재하지 않으면 오류가 발생한다.
	 * 		요청파라미터값을 해당 타입으로 변환할 수 없을 때 오류가 발생한다.
	 * @RequestParam
	 * 		요청파라미터값을 요청핸들러의 매개변수와 매핑시키는 어노테이션이다.
	 * 		주요 속성
	 * 			name			: 요청파라미터의 이름을 지정한다.
	 * 			required		: 기본값은 true다. false로 지정하면 name에 지정한 요청파라미터값이 없어도 오류가 발생하지 않는다.
	 * 			defaultValue	: name에 지정한 요청파라미터값이 존재하지 않을 때 매개변수로 대입되는 기본값을 설정한다.
	 * 							  defaultValue의 값은 문자열로 설정되지만 매개변수에 대입될 때는 해당 타입으로 형변환된다.
	 * 		예시
	 * 			public String list(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
	 * 								@RequestParam(name = "rows", required = false, defaultValue = "10") int rows,
	 * 								@RequestParam(name = "sort", required = false, defaultValue = "date") String sort,
	 * 								String opt,
	 * 								String keyword)
	 * 요청핸들러 메소드의 매개변수로 가능한 객체 및 어노테이션
	 * 		HttpServletRequest			요청객체
	 * 		HttpServletResponse			응답객체
	 * 		HttpSession					세션객체
	 * 		WebRequest					Spring이 제공하는 객체다. 요청객체가 가지고 있는 정보 대부분을 제공하는 객체다.
	 * 		TimeZone					시간정보
	 * 		Locale						지역정보(국가, 언어)
	 * 		InputStream					클라이언트와 연결된 읽기전용 스트림
	 * 		OutputStream				클라이언트와 연결된 쓰기 전용 스트림
	 * 		Reader						클라이언트와 연결된 텍스트 읽기 전용 스트림
	 * 		Writer						클라이언트와 연결된 텍스트 쓰기 전용 스트림
	 * 		@RequestParam				요청파라미터와 매개변수를 매핑시키는 어노테이션
	 * 		@PathVariable				요청 URL 경로에 포함된 파라미터값과 매개변수를 매핑시키는 어노테이션
	 * 		@ModelAttribute				요청파라미터와 해당값을 저장하는 객체를 매핑시키는 어노테이션
	 * 		@RequestBody				요청메시지의 바디부 정보와 매개변수를 매핑시키는 어노테이션
	 * 		@Valid						요청파라미터값의 유효성 여부를 검증시키는 어노테이션
	 * 		Model						뷰에 전달할 데이터를 저장하는 객체를 전달받는다
	 * 		Errors						요청파라미터값의 유효성 검증 결과를 저장하는 객체
	 * 		BindingResult				요청파라미터값의 유효성 검증 결과를 저장하는 객체
	 * 		SessionStatus				세션에 저장된 정보를 삭제하는 객체
	 * 		기본자료형						요청파라미터값을 전달받는다.
	 * 		String						요청파라미터값을 전달받는다.
	 * 		사용자정의 객체					요청파라미터값을 전달받는다.
	 */
	@PostMapping("/login")	//post방식이면서 login에 매핑, login-form에 action이 여기에 매핑, post, login
	public String login(String id, String password) {
		// 아이디, 비밀번호로 로그인 검증
		User user = userService.login(id, password); //로그인에 성공했다 하면 user객체를 반환받는다.
		
		// HttpSession 객체에 속성으로 로그인 절차가 완료된 사용자 정보를 저장한다.
		LoginUserInfo loginUserInfo = new LoginUserInfo(user.getId(), user.getName());
		SessionUtils.setAttribute("loginUser", loginUserInfo);	// 반환받은 객체를 유틸에 만들어둔 세션을 통해 저장시켜준다. 필요할 때마다 꺼내쓰기
		
		return "redirect:home";
	}
	
	/*
	 * <요청 URL 매핑하기>
	 * 	요청 URL과 요청핸들러 메소들르 매핑시키는 어노테이션
	 * 		@RequestMapping : 요청방식에 상관없이 요청 URL을 기준으로 매핑시킨다.
	 * 		@GetMapping		: 요청방식이 GET 방식이고 요청 URL이 일치하는 요청핸들러 메소드와 매핑시킨다.
	 * 		@PostMapping	: 요청방식이 POST 방식이고 요청 URL이 일치하는 요청핸들러 메소드와 매핑시킨다.
	 * 		@PutMapping		: 요청방식이 PUT 방식이고 요청 URL이 일치하는 요청핸들러 메소드와 매핑시킨다.
	 * 		@DeleteMapping	: 요청방식이 DELETE 방식이고 요청 URL이 일치하는 요청핸들러 메소드와 매핑시킨다.
	 * 
	 * 요청방식
	 * 		GET		: 서버에서 정보를 조회하는 요청
	 * 		POST	: 서버에 정보를 추가하는 요청
	 * 		PUT		: 서버의 정보를 변경하는 요청
	 * 		DELETE	: 서버의 정보를 삭제하는 요청
	 * 	* 일반적인 웹 애플리케이션에서는 GET,POST 두가지 방식을 사용한다.
	 * 	* Rest 방식의 웹 애플리케이션에서는 GET, POST, PUT, DELETE 방식을 전부 사용한다.
	 */
	@RequestMapping("/logout")	// return이 없을때는 이 logout 경로를 사용해버리는 것.
	public String logout() {
		// HttpSession객체에서 로그인된 사용자 정보를 삭제한다.
		SessionUtils.removeAttribute("loginUser");	//세션을 파괴하는 것.
		
		return "redirect:home";
	}

}
