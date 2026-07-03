package com.cmc.util.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(int httpStatusCode, String message, String timestamp) {

}
