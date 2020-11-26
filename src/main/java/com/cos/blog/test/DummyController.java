package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@Controller
public class DummyController {
 
	private UserRepository userRepository;
	
	@PostMapping("/dumm/join")
	public String join(User user) { //key=value(약속된 규칙)
		System.out.println("id :" + user.getId());
		System.out.println("username :" + user.getUsername());
		System.out.println("password :" + user.getPassword());
		System.out.println("email :" + user.getEmail());
		System.out.println("createDate :" + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		   
		return "회원가입이 완료되었습니다.";
	}
	
	@GetMapping("/")
	public String main() {
	return "main";
	}
	
	@GetMapping("/user/joinForm")
	public String join() {
	return "user/joinForm";
	}
	
	@GetMapping("/user/loginForm")
	public String login() {
	return "user/loginForm";
	}
	
}