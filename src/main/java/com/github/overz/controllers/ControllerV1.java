package com.github.overz.controllers;

import com.github.overz.api.V1ApiDelegate;
import com.github.overz.configs.TemplateEnum;
import com.github.overz.generators.DocumentGenerator;
import com.github.overz.generators.EngineGenrator;
import com.github.overz.models.Book;
import com.github.overz.presentation.representation.DocumentEncodedRepresentation;
import com.github.overz.utils.templates.BookTemplateVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class ControllerV1 implements V1ApiDelegate {
	private List<Book> books(int size) {
		return Stream.generate(() -> Book.builder()
				.name(RandomStringUtils.randomAlphabetic(15, 200))
				.author(RandomStringUtils.randomAlphabetic(15, 200))
				.description(RandomStringUtils.randomAlphabetic(15, 200))
				.createdAt(LocalDate.now())
				.publishedAt(LocalDate.now())
				.build()
			)
			.limit(size)
			.toList();
	}

	private final EngineGenrator template;
	private final DocumentGenerator document;

	private Map<String, Object> getVariables(String name, Integer size) {
		return Map.of(
			BookTemplateVariables.BOOKS, books(size),
			BookTemplateVariables.USERNAME, name
		);
	}

	@Override
	public ResponseEntity<DocumentEncodedRepresentation> getPdfEncoded(String id, String name, Integer size, String lang) {
		var content = document.generate(
			template.generate(
				TemplateEnum.parse(id),
				getVariables(name, size)
			)
		);

		return ResponseEntity.ok(new DocumentEncodedRepresentation(
			Base64.getEncoder().encodeToString(content)
		));
	}

	@Override
	public ResponseEntity<Resource> getPdfPreview(String id, String name, Integer size, String lang) {
		var bin = document.generate(
			template.generate(
				TemplateEnum.parse(id),
				getVariables(name, size)
			)
		);

		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline()
			.filename("document.pdf", StandardCharsets.UTF_8)
			.build()
		);

		return new ResponseEntity<>(
			new ByteArrayResource(bin),
			headers,
			HttpStatus.OK
		);
	}

	@Override
	public ResponseEntity<String> getTemplate(String id, String name, Integer size, String locale) {
		return ResponseEntity.ok(
			template.generate(
				TemplateEnum.parse(id),
				getVariables(name, size)
			)
		);
	}
}
