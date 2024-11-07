package com.github.overz.configs;

import com.github.overz.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Configuration
public class DatabaseConfig {

	@Bean
	public Jdbi jdbi(DataSource ds, List<JdbiPlugin> plugins, List<RowMapper<?>> mappers) {
		log.info("Configuring JDBI with datasource '{}'", ds);
		var jdbi = Jdbi.create(new TransactionAwareDataSourceProxy(ds));
		plugins.forEach(jdbi::installPlugin);
		mappers.forEach(jdbi::registerRowMapper);
		return jdbi;
	}

	@Bean
	public JdbiPlugin sqlObjectPlugin() {
		return new SqlObjectPlugin();
	}

	@Bean
	public BookRepository bookRepository(Jdbi jdbi) {
		log.info("Configuring repository '{}'", BookRepository.class.getSimpleName());
		return jdbi.onDemand(BookRepository.class);
	}
}
