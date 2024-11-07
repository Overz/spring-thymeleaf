package com.github.overz.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.MissingResourceException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	public ResponseEntity<Object> handleWrapperError(ApplicationError e, WebRequest request) {
		return handleExceptionInternal(e, e.getBody(), e.getHeaders(), e.getStatusCode(), request);
	}

	@ExceptionHandler({ApplicationError.class})
	public ResponseEntity<Object> handleTemplateGenerationException(ApplicationError ex, WebRequest request) {
		return handleExceptionInternal(ex, null, ex.getHeaders(), ex.getStatusCode(), request);
	}

	@ExceptionHandler({MissingResourceException.class})
	public ResponseEntity<Object> handleMissingResourceException(MissingResourceException ex, WebRequest request) {
		var e = new ApplicationError("No resource found", ex, INTERNAL_SERVER_ERROR, new HttpHeaders(), null, MissingResourceException.class);
		return handleWrapperError(e, request);
	}
}
