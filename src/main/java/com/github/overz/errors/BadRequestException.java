package com.github.overz.errors;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends ApplicationError {
	public BadRequestException(String message) {
		this(message, null);
	}

	public BadRequestException(String message, Map<String, Object> properties) {
		super(message, null, HttpStatus.BAD_REQUEST, null, properties, BadRequestException.class);
	}
}
