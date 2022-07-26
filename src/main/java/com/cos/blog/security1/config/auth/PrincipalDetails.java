package com.cos.blog.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.security1.model.User;

// 시큐리티가 /login낚아채서 로그인 진행
// 진행완료되면 시큐리티가 가지고 있는 시큐리티 컨텍스트 홀더 내부의 session에 authentication 객체 넣어줘야 한다.(이 타입의 객체만 가능)
// authentication 안에 User 정보가 있어야 한다.
// User 오브젝트 타입은 UserDetails 타입 객체여야 한다.

// Security Session ==> Authentication => UserDetails(이게 User 오브젝트라고)

public class PrincipalDetails implements UserDetails{
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 해당 User의 권한을 리턴하는 곳
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
	
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 1년 동안 로그인 안 할경우 비활성화 뭐 이런 로직 추가 해주어야 한다.
		return true;
	}

}
