package com.github.overz.mappers;

import com.github.overz.dto.BookDTO;
import com.github.overz.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
	componentModel = "spring"
)
public interface BookMapper {

	@Mapping(target = "cdBook", source = "id")
	@Mapping(target = "nmBook", source = "name")
	@Mapping(target = "nmAuthor", source = "author")
	@Mapping(target = "dsDescription", source = "description")
	@Mapping(target = "dtCreatedAt", source = "createdAt")
	@Mapping(target = "dtPublishedAt", source = "publishedAt")
	Book toModel(BookDTO o);

	@Mapping(target = "id", source = "cdBook")
	@Mapping(target = "name", source = "nmBook")
	@Mapping(target = "author", source = "nmAuthor")
	@Mapping(target = "description", source = "dsDescription")
	@Mapping(target = "createdAt", source = "dtCreatedAt")
	@Mapping(target = "publishedAt", source = "dtPublishedAt")
	BookDTO toDTO(Book o);

	@Mapping(target = "cdBook", source = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "nmBook", source = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "nmAuthor", source = "author", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "dsDescription", source = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "dtCreatedAt", source = "createdAt", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "dtPublishedAt", source = "publishedAt", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateModel(@MappingTarget Book a, BookDTO b);

	List<Book> toListModel(List<BookDTO> o);

	List<BookDTO> toListDTO(List<Book> o);
}
