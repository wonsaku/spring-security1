package com.cos.blog.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에등록
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true) //secured 어노테이션 활성화 preAuthorized 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Bean //리턴되는 오브젝트를 IoC로 등록
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated()
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/loginForm")
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/"); // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 //특정페이지를 요청해서 거기서 login으로 온거면 거기로 보내주고
		//그냥 loginForm으로 들어왔으면 디폴트로 /로 보내준다.
	}

	
	
}
