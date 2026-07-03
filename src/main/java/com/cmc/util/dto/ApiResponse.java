package com.cmc.util.dto;

import lombok.Builder;

@Builder
public record ApiResponse<T>(T data, String message, int httpStatusCode, String timestamp) {

}
