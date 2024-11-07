package com.github.overz.controllers;

import com.github.overz.api.DocumentApiDelegate;
import com.github.overz.configs.TemplateEnum;
import com.github.overz.dto.DocumentEncodedDTO;
import com.github.overz.generators.DocumentGenerator;
import com.github.overz.generators.EngineGenrator;
import com.github.overz.services.BookService;
import com.github.overz.utils.templates.BookTemplateVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class DocumentController implements DocumentApiDelegate {
	private final EngineGenrator engine;
	private final DocumentGenerator document;
	private final BookService service;

	private Map<String, Object> getVariables(String name) {
		return Map.of(
			BookTemplateVariables.BOOKS, service.list(),
			BookTemplateVariables.USERNAME, name
		);
	}

	@Override
	public ResponseEntity<String> getTemplate(String id, String name, String lang) {
		return ResponseEntity.ok(
			engine.generate(
				TemplateEnum.parse(id),
				getVariables(name)
			)
		);
	}

	@Override
	public ResponseEntity<DocumentEncodedDTO> getPdfEncoded(String id, String name, String lang) {
		var content = document.generate(
			engine.generate(
				TemplateEnum.parse(id),
				getVariables(name)
			)
		);

		return ResponseEntity.ok(new DocumentEncodedDTO(
			Base64.getEncoder().encodeToString(content)
		));
	}

	@Override
	public ResponseEntity<Resource> getPdfPreview(String id, String name, String lang) {
		var bin = document.generate(
			engine.generate(
				TemplateEnum.parse(id),
				getVariables(name)
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
}
