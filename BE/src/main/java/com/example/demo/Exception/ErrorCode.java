package com.example.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INTERNAL_SERVER_ERROR(1000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
	SUCCESS(9999, "Success", HttpStatus.OK),
	PRODUCT_NOT_FOUND(2001, "Product not found", HttpStatus.NOT_FOUND),
	REQUIRED_PRODUCT_NAME(2002, "REQUIRED_PRODUCT_NAME", HttpStatus.BAD_REQUEST),
	OVER_MAX_DESCRIPTION_LENGTH(2003, "OVER_MAX_DESCRIPTION_LENGTH", HttpStatus.BAD_REQUEST),
	INVALID_PRODUCT_PRICE(2004, "INVALID_PRODUCT_PRICE", HttpStatus.BAD_REQUEST),
	INVALID_PRODUCT_QUANTITY(2005, "INVALID_PRODUCT_QUANTITY", HttpStatus.BAD_REQUEST),
	REQUIRED_PRODUCT_PRICE(2006, "REQUIRED_PRODUCT_PRICE", HttpStatus.BAD_REQUEST),
	REQUIRED_PRODUCT_CATEGORY(2007, "REQUIRED_PRODUCT_CATEGORY", HttpStatus.BAD_REQUEST),
	REQUIRED_PRODUCT_IMAGE_URL(2008, "REQUIRED_PRODUCT_IMAGE_URL", HttpStatus.BAD_REQUEST),
	FORBIDDEN(3001, "Forbidden", HttpStatus.FORBIDDEN);
	private final int code;
	private final String message;
	private final HttpStatus httpStatus;
}
