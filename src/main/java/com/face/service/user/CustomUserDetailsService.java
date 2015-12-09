package com.face.service.user;

import com.face.model.user.User;

public interface CustomUserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

	public  User loadUserByUsername(String username);

	public User save(User user);
	public  User findOne(Long userId);
}
