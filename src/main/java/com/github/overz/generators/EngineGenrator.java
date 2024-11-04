package com.github.overz.generators;

import com.github.overz.configs.TemplateEnum;

import java.util.Map;

@FunctionalInterface
public interface EngineGenrator {
	String generate(TemplateEnum t, Map<String, Object> vars);
}
