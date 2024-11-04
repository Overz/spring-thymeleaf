package com.github.overz.controllers;

import com.github.overz.api.V1ApiDelegate;
import com.github.overz.configs.TemplateEnum;
import com.github.overz.generators.DocumentGenerator;
import com.github.overz.generators.EngineGenrator;
import com.github.overz.models.Book;
import com.github.overz.presentation.representation.PdfRepresentation;
import com.github.overz.utils.templates.BookTemplateVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ControllerV1 implements V1ApiDelegate {
	private static final List<Book> books = List.of(
		new Book("a", "a", "a", LocalDate.now(), LocalDate.now()),
		new Book("b", "b", "b", LocalDate.now(), LocalDate.now()),
		new Book("c", "c", "c", LocalDate.now(), LocalDate.now()),
		new Book("d", "d", "d", LocalDate.now(), LocalDate.now()),
		new Book("e", "e", "e", LocalDate.now(), LocalDate.now())
	);

	private final EngineGenrator template;
	private final DocumentGenerator document;

	@Override
	public ResponseEntity<PdfRepresentation> getPdf(String id, String name, String locale) {
		var content = document.generate(
			template.generate(
				TemplateEnum.parse(id),
				Map.of(
					BookTemplateVariables.BOOKS, books,
					BookTemplateVariables.USERNAME, name
				)
			)
		);

		return ResponseEntity.ok(new PdfRepresentation(content));
	}

	@Override
	public ResponseEntity<String> getTemplate(String id, String name, String locale) {
		return ResponseEntity.ok(
			template.generate(
				TemplateEnum.parse(id),
				Map.of(
					BookTemplateVariables.BOOKS, books,
					BookTemplateVariables.USERNAME, name
				)
			)
		);
	}
}
