package com.cmc.auth.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmc.auth.dto.UserDTO;
import com.cmc.auth.entity.User;
import com.cmc.auth.entity.UserRole;
import com.cmc.auth.repository.UserRepository;
import com.cmc.auth.repository.UserRoleRepository;
import com.cmc.util.enums.EUserRole;
import com.cmc.util.enums.HttpErrorCode;
import com.cmc.util.exception.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService{

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDTO createUser(UserDTO dto) {
		UserRole userRole = userRoleRepository.findByName(EUserRole.valueOf(dto.getRoleName()))
				.orElseThrow(()-> new BaseException(HttpErrorCode.NOT_FOUND, "User Role not found"));
		try {
			User user = User.builder()
					.userName(dto.getUserName())
					.password(passwordEncoder.encode(dto.getPassword()))
					.role(userRole)
					.build();
			userRepository.save(user);
			return dto;
		} catch (Exception e) {
			throw new BaseException(HttpErrorCode.INTERNAL_ERROR, "Create new User failed");
		}
	}

}
