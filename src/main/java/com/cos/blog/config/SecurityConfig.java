package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//빈 등록:스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration //빈등록(Ioc관리)
@EnableWebSecurity //시큐리티 필터 등록 = 스프링 시큐리티가 활성화가 되어있는데 어떤 설정을 해당 파일에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //ioc가 됨
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야 같은 해쉬로 암호화 해서 db에있는 해쉬랑 비교가능
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
		.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리틱 해당주소로 요청하는 로그인을 가로채서 대신 로그인해줌
			.defaultSuccessUrl("/"); // 로그인이 끝나면 해당 주소로 간다.
	}
	

}
