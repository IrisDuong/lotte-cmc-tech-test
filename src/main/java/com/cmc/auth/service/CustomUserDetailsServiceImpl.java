package com.cmc.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.auth.config.UserDetailsCoreImpl;
import com.cmc.auth.entity.User;
import com.cmc.auth.repository.UserRepository;
import com.cmc.util.enums.HttpErrorCode;
import com.cmc.util.exception.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User existedUser = userRepository.findByUserName(username)
				.orElseThrow(()-> new BaseException(HttpErrorCode.NOT_FOUND, "User not found"));
		return UserDetailsCoreImpl.build(existedUser);
	}
	
}
