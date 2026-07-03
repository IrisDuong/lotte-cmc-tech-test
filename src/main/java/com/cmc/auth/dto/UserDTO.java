package com.cmc.auth.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
	
	@NotBlank(message = "Username is required")
	private String userName;
	
	@JsonProperty(access =  Access.WRITE_ONLY)
	@NotBlank(message = "Username is required")
	private String password;

	
	@NotBlank(message = "Username is required")
	private String roleName;
}
