package com.github.overz.errors;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationError {
	public NotFoundException(String message) {
		this(message, null);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause, HttpStatus.NOT_FOUND, null, null, NotFoundException.class);
	}
}
