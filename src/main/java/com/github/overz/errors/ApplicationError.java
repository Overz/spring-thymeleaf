package com.github.overz.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.util.Assert;
import org.springframework.web.ErrorResponse;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationError extends RuntimeException implements ErrorResponse {
	private final HttpStatus status;
	private final HttpHeaders headers;
	private final Class<?> cls;
	private final ProblemDetail problem;

	public ApplicationError(
		String message,
		Throwable cause,
		HttpStatus status,
		HttpHeaders headers,
		Map<String, Object> properties,
		Class<?> cls
	) {
		super(message, cause);
		Assert.notNull(status, "status must not be null");
		Assert.notNull(cls, "class must not be null");

		this.status = status;
		this.headers = headers != null ? headers : new HttpHeaders();
		this.cls = cls;
		this.problem = ProblemDetail.forStatus(status);
		this.problem.setProperties(properties);
	}

	@Override
	public HttpStatusCode getStatusCode() {
		return this.status;
	}

	@Override
	public HttpHeaders getHeaders() {
		return this.headers;
	}

	@Override
	public ProblemDetail getBody() {
		return this.problem;
	}

	@Override
	public String getTypeMessageCode() {
		return "problemDetail.type.errors." + cls.getSimpleName();
	}

	@Override
	public String getTitleMessageCode() {
		return "problemDetail.title.errors." + cls.getSimpleName();
	}

	@Override
	public String getDetailMessageCode() {
		return "problemDetail.detail.errors." + cls.getSimpleName();
	}
}
