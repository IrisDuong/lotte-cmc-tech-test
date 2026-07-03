package com.cmc.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponseDTO(
		String userName,
		String accessToken,
		String refreshToken
		
		) {

}
