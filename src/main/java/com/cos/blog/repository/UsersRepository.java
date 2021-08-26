package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;


//dao랑 같은의미
//자동으로 bean으로 등록이된다.
//@Repository 생략가능
public interface UsersRepository extends JpaRepository<User, Integer> {

	//select * from user WHERE username=1?;
	
	User findByUsername(String username);
}