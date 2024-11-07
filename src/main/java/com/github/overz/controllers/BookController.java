package com.github.overz.controllers;

import com.github.overz.api.BooksApiDelegate;
import com.github.overz.dto.BookDTO;
import com.github.overz.mappers.BookMapper;
import com.github.overz.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BookController implements BooksApiDelegate {
	private final BookService service;
	private final BookMapper mapper;

	@Override
	public ResponseEntity<BookDTO> postBook(BookDTO bookDTO) {
		return ResponseEntity.ok(mapper.toDTO(service.save(mapper.toModel(bookDTO))));
	}

	@Override
	public ResponseEntity<Void> putBook(BookDTO bookDTO) {
		service.update(mapper.toModel(bookDTO));
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> patchBook(BookDTO bookDTO) {
		var o = service.findOne(bookDTO.getId());
		mapper.updateModel(o, bookDTO);
		service.update(o);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<BookDTO> getOneBook(String id) {
		return ResponseEntity.ok(mapper.toDTO(service.findOne(id)));
	}

	@Override
	public ResponseEntity<Void> deleteOneBook(String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<List<BookDTO>> getBooks() {
		return ResponseEntity.ok(mapper.toListDTO(service.list()));
	}
}
