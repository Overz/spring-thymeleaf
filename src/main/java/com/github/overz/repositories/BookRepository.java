package com.github.overz.repositories;

import com.github.overz.models.Book;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@UseClasspathSqlLocator
public interface BookRepository {
	@SqlQuery
	@RegisterBeanMapper(Book.class)
	List<Book> listAll();

	@SqlQuery
	@RegisterBeanMapper(Book.class)
	Optional<Book> findOne(
		@Bind(Book.Fields.cdBook) String id
	);

	@SqlUpdate
	void save(@BindBean Book b);

	@SqlUpdate
	void update(@BindBean Book o);

	@SqlUpdate
	void delete(@Bind(Book.Fields.cdBook) String id);

	@SqlQuery
	Integer exists(@Bind(Book.Fields.cdBook) String id);
}
