package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//스프링이 컴포넌트 스캔을 통해서 bean 에 들록을 해줌 .IOC를 해준다.
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	
	@Transactional
	public void 글쓰기(Board board, User user) { //title,content
		board.setCount(0);
		board.setUser(user);
	boardRepository.save(board);

	}
	
	/*
	 * @Transactional(readOnly =true) //select 할 때 트랜잭션 시작 , 서비스 종료시에 트랜잭션 종료(정합성)
	 * public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */
}
