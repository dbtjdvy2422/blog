package com.cos.blog.model;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor //빈 생성자
@AllArgsConstructor //모든 생성자
@Builder //빌더 패턴
//ORM -> JAVA OBJECT  -> 테이블로 매핑해주는 기술 
@Entity//user클래스가 mysql에 테이블이 생성된다. 
//@DynamicInsert //insert시에 null인 필드를 제회시켜준다.
public class User {

	@Id //Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//해당 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스 ,auto_increment
	
	@Column(nullable = false,length =30)
	private String username; //아이디
	
	@Column(nullable = false, length =100) //123456 =>해쉬( 비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length =50)
	private String email;
	
	//@ColunmDefalut("'user'")
	//DB는 RoleType이라는게 업다. 그래서 @Enumerated(EnumType.STRING)으로 스트링값이라는걸 알려준다.
	@Enumerated(EnumType.STRING)
	private RoleType role; //enum을 쓰는게 좋다.//admin, user( enum을 쓰면 설정헤준  두 가지만 넣을 수 있다) 
	
	@CreationTimestamp //시간 자동 입력
	private Timestamp createDate;
}
