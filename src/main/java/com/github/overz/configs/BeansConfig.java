package com.github.overz.configs;

import com.github.overz.api.BooksApiDelegate;
import com.github.overz.api.DocumentApiDelegate;
import com.github.overz.controllers.BookController;
import com.github.overz.controllers.DocumentController;
import com.github.overz.generators.DocumentGenerator;
import com.github.overz.generators.EngineGenrator;
import com.github.overz.generators.PdfDocumentGenerator;
import com.github.overz.generators.TemplateEngineGenerator;
import com.github.overz.mappers.BookMapper;
import com.github.overz.repositories.BookRepository;
import com.github.overz.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Slf4j
@Configuration
public class BeansConfig {

	@Bean
	public EngineGenrator engineGenrator(
		final SpringTemplateEngine engine
	) {
		log.info("Configuring template engine '{}'", TemplateEngineGenerator.class.getSimpleName());
		return new TemplateEngineGenerator(engine);
	}

	@Bean
	public DocumentGenerator pdfDocumentGenerator() {
		log.info("Configuring document generator '{}'", PdfDocumentGenerator.class.getSimpleName());
		final var renderer = new ITextRenderer();
		return new PdfDocumentGenerator(renderer);
	}

	@Bean
	public BookService bookService(
		final BookRepository repository
	) {
		log.info("Configuring service '{}'", BookService.class.getSimpleName());
		return new BookService(repository);
	}

	@Bean
	public BooksApiDelegate booksApiDelegate(
		final BookService service,
		final BookMapper mapper
		) {
		log.info("Configuring controller '{}'", BookController.class.getSimpleName());
		return new BookController(service, mapper);
	}

	@Bean
	public DocumentApiDelegate documentApiDelegate(
		final EngineGenrator engineGenrator,
		final DocumentGenerator documentGenerator,
		final BookService service
	) {
		return new DocumentController(engineGenrator, documentGenerator, service);
	}
}
