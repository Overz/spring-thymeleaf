package com.github.overz.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
	@Bean
	@ConfigurationProperties("spring.messages")
	public MessageSourceProperties messageSourceProperties() {
		return new MessageSourceProperties();
	}

	@Bean
	public MessageSource messageSource(MessageSourceProperties properties) {
		var source = new ReloadableResourceBundleMessageSource();
		source.setBasename(properties.getBasename());
		source.setDefaultEncoding(properties.getEncoding().name());
		source.setCacheMillis(properties.getCacheDuration().toMillis());
		source.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
		source.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
		source.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
		return source;
	}

	@Bean
	public LocaleResolver localeResolver() {
		log.info("Configuring '{}'", LocaleResolver.class.getSimpleName());
		var resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		log.info("Configuring '{}'", LocaleChangeInterceptor.class.getSimpleName());
		var interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		interceptor.setIgnoreInvalidLocale(true);
		return interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
