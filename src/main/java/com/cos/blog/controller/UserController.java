package com.cos.blog.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.compiler.ast.Variable;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥
@Controller
public class UserController {
 
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private BoardService boardService;
	
	private PrincipalDetail principal;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
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
	@CrossOrigin
	@GetMapping({"","/"})
	public String main(Model model, @PageableDefault(size=1, sort="id",direction =Sort.Direction.DESC) Pageable pageable) {
	model.addAttribute("boards",boardService.글목록(pageable));
	return "main";
	}
	
	@GetMapping("board/{id}")
	public String findById(Model model,  @PathVariable int id) {
		
		model.addAttribute("board",boardService.상세보기(id));
		return "board/detail";
		
	}
	
	
	@GetMapping("/auth/joinForm")
	public String join() {
	return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String login() {
	return "user/loginForm";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
	return "board/saveForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
	return "user/updateForm";
	}
	
	@GetMapping("auth/kakao/callback")
	public String kakaoCallback(String code) { //data를 리턴해주는 컨트롤러 함수
		//Post방식으로 key=value 데이터를 요청(카카오쪽으로)
		//Retrofit2
		//OkHttp
		//RestTemplate
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
	
		//httpBody 오브젝트 생성
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","57ea8b244103264c206819e7f856ddcc");
		params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
		params.add("code",code);
		
		//httpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		//Http 요청하기 = post방식으로 - 그리고 reponse변수의 응답받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				);
		
		//Gson, Json, Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(" 카카오 엑세스 토큰: " + oauthToken.getAccess_token());
		
		
	RestTemplate rt2 = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
	
		
		//httpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		//Http 요청하기 - post방식으로 - 그리고 reponse변수의 응답받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
				);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호): "+kakaoProfile.getId());
		System.out.println("카카오 이메일: "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그 유저네임: "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그 서버네임: "+kakaoProfile.getKakao_account().getEmail());
		UUID garbagePassword = UUID.randomUUID();
		System.out.println("블로그 패스워드: " +cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//가입자 혹은 비가입자 체크 해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername()==null) {
			System.out.println("기존 회원이 아니기에 회원가입을 진행합니다.");
			userService.회원가입(kakaoUser);
		}
		
		//로그인처리

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/"; 
		
		
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.상세보기(id));
		return "board/updateForm";
		
	} 
	

	
	
}