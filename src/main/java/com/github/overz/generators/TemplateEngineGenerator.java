package com.github.overz.generators;

import com.github.overz.configs.TemplateEnum;
import com.github.overz.errors.TemplateGenerationException;
import com.github.overz.utils.FormattersUtils;
import com.github.overz.utils.templates.CommonsVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TemplateEngineGenerator implements EngineGenrator {
	private final SpringTemplateEngine engine;

	@Override
	public final String generate(final TemplateEnum t, final Map<String, Object> vars) {
		log.info("Trying to generate the template");
		try {
			var args = update(t, vars);
			log.info("Generating template '{}' and '{}' variables", t.getId(), args.size());
			var content = engine.process(
				t.getId(),
				new Context(LocaleContextHolder.getLocale(), args)
			);

			log.info("Template was generated successfully from '{}' using '{}' variables", t.getId(), args.size());
			return content;
		} catch (Exception e) {
			throw new TemplateGenerationException("Error generating tempalte '" + t.getId() + "' with variables '" + vars + "'", e);
		}
	}

	protected Map<String, Object> update(final TemplateEnum t, final Map<String, Object> vars) {
		if (TemplateMode.HTML != t.getType() && TemplateMode.XML != t.getType()) {
			log.warn("Template is not an HTML or XML, skipping variables update");
			return vars;
		}

		log.info("Updating '{}' variables from template '{}'", vars.size(), t.getId());
		var updated = new HashMap<>(vars);
		updated.putAll(Map.of(
			CommonsVariables.LANG, LocaleContextHolder.getLocale().getLanguage(),
			CommonsVariables.GENERATION_TIME, LocalDateTime.now().format(FormattersUtils.yyyy_MM_dd_HH_mm_ss),
			t.getId() + "_style", getResource(t.getStylePath(), ResourceType.TEXT),
			t.getId() + "_script", getResource(t.getScriptPath(), ResourceType.TEXT),
			t.getId() + "_img", getResource(t.getImagePath(), ResourceType.ASSETS)
		));

		return updated;
	}

	protected Object getResource(final String path, ResourceType type) {
		log.info("Loading resource in classpath form path '{}'", path);
		var resource = new ClassPathResource(path);
		try (var is = resource.getInputStream()) {
			return switch (type) {
				case TEXT -> {
					var content = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
					yield content.replaceAll("[\n\t]", "").trim();
				}
				case ASSETS -> {
					var content = StreamUtils.copyToByteArray(resource.getInputStream());
					yield Base64.getEncoder().encodeToString(content);
				}
				default ->
					throw new IllegalStateException("Unexpected value '" + type + "' to recovery the resource from '" + path + "'");
			};
		} catch (Exception e) {
			throw new TemplateGenerationException("Error reading resource '" + path + "'", e);
		}
	}

	protected enum ResourceType {
		NONE,
		TEXT,
		ASSETS
	}
}
