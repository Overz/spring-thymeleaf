package com.github.overz.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Book implements Serializable {
	@Null(message = Book.Fields.cdBook + " must be null")
	@ColumnName(Fields.cdBook)
	private String cdBook;
	@NotEmpty(message = Fields.nmBook + " can't be empty")
	@ColumnName(Fields.nmBook)
	private String nmBook;
	@NotEmpty(message = Fields.nmAuthor + " can't be empty")
	@ColumnName(Fields.nmAuthor)
	private String nmAuthor;
	@NotEmpty(message = Fields.dsDescription + " can't be empty")
	@ColumnName(Fields.dsDescription)
	private String dsDescription;
	@NotNull(message = Fields.dtCreatedAt + " can't be empty")
	@ColumnName(Fields.dtCreatedAt)
	private LocalDate dtCreatedAt;
	@NotNull(message = Fields.dtPublishedAt + " can't be empty")
	@ColumnName(Fields.dtPublishedAt)
	private LocalDate dtPublishedAt;

	@Override
	public String toString() {
		return String.format(
			"%s(%s=%s, %s=%s, %s=%s, %s=%s, %s=%s, %s=%s)",
			getClass().getSimpleName(),
			Book.Fields.cdBook, cdBook,
			Book.Fields.nmBook, nmBook,
			Book.Fields.nmAuthor, nmAuthor,
			Book.Fields.dsDescription, dsDescription,
			Book.Fields.dtCreatedAt, dtCreatedAt,
			Book.Fields.dtPublishedAt, dtPublishedAt
		);
	}
}
