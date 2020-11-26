package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//dao랑 같은의미
//자동으로 bean으로 등록이된다.
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
