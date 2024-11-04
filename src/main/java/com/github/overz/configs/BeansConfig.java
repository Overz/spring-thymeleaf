package com.github.overz.configs;

import com.github.overz.api.V1ApiDelegate;
import com.github.overz.controllers.ControllerV1;
import com.github.overz.generators.DocumentGenerator;
import com.github.overz.generators.TemplateEngineGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BeansConfig {

	@Bean
	public V1ApiDelegate v1ApiDelegate(
		TemplateEngineGenerator templateEngineGenerator,
		DocumentGenerator documentGenerator
	) {
		return new ControllerV1(templateEngineGenerator, documentGenerator);
	}
}
