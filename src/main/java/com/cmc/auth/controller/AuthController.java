package com.cmc.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.auth.config.JwtTokenProvider;
import com.cmc.auth.config.UserDetailsCoreImpl;
import com.cmc.auth.dto.AuthResponseDTO;
import com.cmc.auth.dto.LoginRequestDTO;
import com.cmc.util.dto.ApiResponse;
import com.cmc.util.enums.TokenType;
import com.cmc.util.func.ApiUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest){
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.userName(), loginRequest.password());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		String accessToken = jwtTokenProvider.genTokenFromAuthen(authentication, TokenType.AT);
		String refreshToken = jwtTokenProvider.genTokenFromAuthen(authentication, TokenType.RT);
		
		UserDetailsCoreImpl userDetailsCoreImpl = (UserDetailsCoreImpl) authentication.getPrincipal();
		AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
				.userName(userDetailsCoreImpl.getUsername())
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
		
		return ApiUtils.buildApiResponse(authResponseDTO, HttpStatus.OK, "Login successfully");
	}
}
