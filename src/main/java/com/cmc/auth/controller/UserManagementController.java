package com.cmc.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.auth.dto.UserDTO;
import com.cmc.auth.service.AuthUserServiceImpl;
import com.cmc.util.dto.ApiResponse;
import com.cmc.util.func.ApiUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/userMgmt")
@RequiredArgsConstructor
public class UserManagementController {
	private final AuthUserServiceImpl userServiceImpl;
	
	@PostMapping("/user")
	public ResponseEntity<ApiResponse<UserDTO>> createAccount (@Valid @RequestBody UserDTO dto){
		UserDTO result = userServiceImpl.createUser(dto);
		return ApiUtils.buildApiResponse(result, HttpStatus.CREATED, "Create new User successfully");
	}
}
