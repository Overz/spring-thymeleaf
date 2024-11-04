package com.github.overz.errors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class TemplateGenerationException extends ApplicationError {
	public TemplateGenerationException(String message) {
		this(message, null);
	}

	public TemplateGenerationException(String message, Throwable cause) {
		super(message, cause, INTERNAL_SERVER_ERROR, null, null, TemplateGenerationException.class);
	}
}
