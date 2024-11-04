package com.github.overz.errors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class DocumentGenerationException extends ApplicationError {
	public DocumentGenerationException(String message, Throwable cause) {
		super(message, cause, INTERNAL_SERVER_ERROR, null, null, DocumentGenerationException.class);
	}
}
