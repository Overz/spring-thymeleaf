package com.github.overz.services;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.overz.errors.BadRequestException;
import com.github.overz.errors.NotFoundException;
import com.github.overz.models.Book;
import com.github.overz.repositories.BookRepository;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
public class BookService {
	private final BookRepository repo;

	@Retry(name = "book-repository")
	public List<Book> list() {
		return repo.listAll();
	}

	@Retry(name = "book-repository")
	public Book findOne(@Valid @NotEmpty String id) {
		return repo.findOne(id).orElseThrow(() -> new NotFoundException("No book with id '" + id + "' was found"));
	}

	@Retry(name = "book-repository")
	public Book save(@Valid Book b) {
		b.setCdBook(NanoIdUtils.randomNanoId());
		b.setDtCreatedAt(LocalDate.now());
		repo.save(b);
		return b;
	}

	@Retry(name = "book-repository")
	public void update(@Valid Book o) {
		repo.update(o);
	}

	@Retry(name = "book-repository")
	public void delete(@Valid @NotEmpty String id) {
		repo.delete(id);
	}

	@Retry(name = "book-repository")
	public void exists(@Valid @NotEmpty String id) {
		var i = repo.exists(id);
		if (i == null || i == 0) {
			throw new NotFoundException("No book with id '" + id + "' was found");
		}
	}
}
