package com.cos.blog.security1.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.security1.model.User;
import com.cos.blog.security1.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"", "/"})
	public String index() {
		//mustache 권장하니까 기본적으로 src/main/resources/여기까지 잡힌다.
		//뷰 리졸버 설정: templates(prefix), .mustache(suffix)
		//경로 생략 가능. 의존성 등록 해두면 저절로 잡힌다. 
		return "index"; //templates/index.mustache
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	@GetMapping("/admin")
	public@ResponseBody  String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public@ResponseBody  String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm")
	public  String loginForm() {
		//여기 오지 못하고 security가 낚아챔
		//security config 파일 후 작동 안함
		return "loginForm";
	}
	
	
	@GetMapping("/joinForm")
	public  String joinForm() {
		//여기 오지 못하고 security가 낚아챔
		//security config 파일 후 작동 안함
		return "joinForm";
	}
	
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user); //비밀번호가 날것으로 들어가면 암호화 안되어서 시큐리티에서 로그인 할 수 없음.
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "개인정보";
	}

}
