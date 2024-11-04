package com.github.overz.generators;

@FunctionalInterface
public interface DocumentGenerator {
	String generate(String template);
}
