package com.example.demo.Exception;

import com.example.demo.DTO.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler  {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleGeneralException(Exception e) {
		ApiResponse<?> response = ApiResponse.builder()
				.code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
				.message(e.getMessage())
				.build();
		return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(response);
	}
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
		ErrorCode errorCode = e.getErrorCode();
		ApiResponse<?> response = ApiResponse.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build();
		return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
		String errorMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
		ErrorCode errorCode = ErrorCode.valueOf(errorMessage);
		ApiResponse<?> response = ApiResponse.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build();
		return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
	}

}
