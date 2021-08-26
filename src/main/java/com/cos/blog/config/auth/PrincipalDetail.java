package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진해하고 완료가 되면 userdetails타입의 오브젝트를 
//스프링 시큐리티의 고유한 세션저장소에 저장을 해줌 

@Data
public class PrincipalDetail implements UserDetails, OAuth2User{
	
	
	private static final long serialVersionUID = 1L;
	private User user;
	private Map<String, Object> attributes;

	public PrincipalDetail(User user) {
		this.user =user;
	}
	

	public PrincipalDetail(User user, Map<String, Object> attributes) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	//계정이 만료되지 않았는지를 리턴한다.(true:만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//계정이 잠겨있지 않았는지 리턴한다(true : 만료안됨)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//비밀번호가 만료되지 않았는지를 리턴한다(true:만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	//계정이 활성화(사용가능)인지 리턴한다(true:활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	
	//계정이 갖고있는 권한 목록을 리턴한다(권한이 여러개 있을 수 있어서 루프를 돌려야 하는데 우리는 한개만있음.(add),여러개면 for문)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> { return user.getRole();});
		return collector;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String) attributes.get("name");
	}
}
