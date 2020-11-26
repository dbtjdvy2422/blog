package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//스프링이 컴포넌트 스캔을 통해서 bean 에 들록을 해줌 .IOC를 해준다.

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 회원가입(User user) {
			userRepository.save(user);

	}
}
