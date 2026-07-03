package com.cmc.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
	    @NotBlank(message = "Username is required")
		String userName,

	    @NotBlank(message = "Password is required")
		String password) {

}
