package com.github.overz.models;

import lombok.*;
import lombok.experimental.FieldNameConstants;

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
	private String name;
	private String author;
	private String description;
	private LocalDate createdAt;
	private LocalDate publishedAt;

	@Override
	public String toString() {
		return String.format(
			"%s(%s=%s, %s=%s, %s=%s, %s=%s, %s=%s)",
			getClass().getSimpleName(),
			Fields.name, name,
			Fields.author, author,
			Fields.description, description,
			Fields.createdAt, createdAt,
			Fields.publishedAt, publishedAt
		);
	}
}
