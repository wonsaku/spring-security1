package com.cos.blog.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.security1.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	 
// crud 함수 들고 있음/
// @Repository 필요없음 JpaRepository 상속받았으니까 알아서 빈등록
	
	
	public User findByUsername(String username); //findBy규칙 Username문법 -> select * from user where username=1? jpa query method
	
	
}
