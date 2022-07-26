package com.cos.blog.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.security1.model.User;
import com.cos.blog.security1.repository.UserRepository;

//시큐리티 설정에서 loginProcessingUrl("/login") /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
// 로그인 시에 파라미터로 username, password 받을텐데  파라미터 바꾸고 싶으면 usernameParameter로 바꾸면 되고
// 시큐리티 session = authentication = UserDetails 그래서 리턴된 값이 시큐리티 session(Authentication(UserDetails))
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username); //이런 아이디 있는지 확인
		System.out.println(userEntity);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}

}
