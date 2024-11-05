package com.github.overz.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.templatemode.TemplateMode;

@Slf4j
@Getter
@AllArgsConstructor
public enum TemplateEnum {
	NONE("", null, null),
	BOOK_TEMPLATE("book_template", TemplateMode.HTML, TemplateMode.CSS),
	;

	private final String id;
	private final TemplateMode type;
	private final TemplateMode style;

	public String getStylePath() {
		return String.format("/static/%s/%s.css", getStyle().name().toLowerCase(), getId());
	}

	public String getScriptPath() {
		return String.format("/static/scripts/%s.js", getId());
	}

	public static TemplateEnum parse(String v) {
		log.info("Trying to parse enum '{}' with value '{}'", TemplateEnum.class.getSimpleName(), v);
		for (var e : values()) {
			if (e.getId().equalsIgnoreCase(v)) return e;
		}

		throw new EnumConstantNotPresentException(TemplateEnum.class, v);
	}

	public String getImagePath() {
		return String.format("/static/assets/%s.png", getId());
	}
}
