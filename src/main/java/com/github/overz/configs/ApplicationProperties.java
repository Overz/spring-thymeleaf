package com.github.overz.configs;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("app")
public class ApplicationProperties implements Serializable {
	private Template template;
	private Internalization internalization;

	@Getter
	@Setter
	@ToString
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Template implements Serializable {
		private Config config;

		@Getter
		@Setter
		@ToString
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Config implements Serializable {
			private String path;
			private Specification html;
			private Specification css;
			private Specification js;

			@Getter
			@Setter
			@ToString
			@Builder
			@NoArgsConstructor
			@AllArgsConstructor
			public static class Specification implements Serializable {
				private String prefix;
				private String suffix;
				@Builder.Default
				private Boolean cacheable = true;
				@Builder.Default
				private Charset encoding = StandardCharsets.UTF_8;
			}
		}
	}

	@Getter
	@Setter
	@ToString
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Internalization implements Serializable {
		private Locale defaultLocale;
	}
}
