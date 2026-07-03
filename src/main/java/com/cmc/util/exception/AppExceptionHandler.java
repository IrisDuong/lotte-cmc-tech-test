package com.cmc.util.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cmc.util.dto.ErrorResponse;
import com.cmc.util.enums.HttpErrorCode;
import com.cmc.util.func.DateUtils;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().httpStatusCode(ex.getHttpErrorCode().getStatusCode())
				.message(ex.getMessage()).timestamp(DateUtils.nowWithDateTime()).build();
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(errorResponse.httpStatusCode()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ErrorResponse errorResponse = ErrorResponse.builder()
				.httpStatusCode(HttpErrorCode.INVALID_REQUEST.getStatusCode()).message(ex.getMessage())
				.timestamp(DateUtils.nowWithDateTime()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
