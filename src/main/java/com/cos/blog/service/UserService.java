package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//스프링이 컴포넌트 스캔을 통해서 bean 에 들록을 해줌 .IOC를 해준다.
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
			return user;
		
	}
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234원문
		String encPassword = encoder.encode(rawPassword); //해쉬가 됨
		user.setPassword(encPassword);
				user.setRole(RoleType.USER);
			userRepository.save(user);

	}
	
	@Transactional
	public void 회원수정(User user) {
		//수정시에는 영속성 컨텍스트 USER오브젝트를 영속화 시키고 영속화되 user 오브젝트를 수정
		//select를 해서 USER 오브젝트를 DB로 부터 자겨오는 이유는 영속화를 하기 위해
		//영속화된 오브젝트를 변경하면 자동으로 DB에 UPDATE문을 날려줌
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		//Validate 체크 -> oauth값이 없으면 수정 가능
		if(persistance.getOauth()==null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		
		
		
		//회원수정 함수 종료시 = 서비스종료 = 트랜잭션 종료 = commit이 자동으로 됩니다.
		//영속화된 persistance 객체가 변화가 감지되면 더티체킹이 되어 update문을 날려줌. 
		
		
		}
	
	
	//세션 등록
	/*
	 * @Transactional(readOnly =true) //select 할 때 트랜잭션 시작 , 서비스 종료시에 트랜잭션 종료(정합성)
	 * public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */
}
