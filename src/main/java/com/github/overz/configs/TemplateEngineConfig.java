package com.github.overz.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Slf4j
@Configuration
public class TemplateEngineConfig {
	private static final int HTML_CHAIN_ORDER = 1;
	private static final int CSS_CHAIN_ORDER = 2;

	@Bean
	public ITemplateResolver htmlTemplateResolver(
		final ApplicationProperties props
	) {
		log.info("Configurando template resolver para HTML: '{}'", props.getTemplate().getConfig().getHtml());
		final var resolver = new SpringResourceTemplateResolver();
		resolver.setName("app-html-template-resolver");
		resolver.setCharacterEncoding(props.getTemplate().getConfig().getHtml().getEncoding().name());
		resolver.setPrefix(props.getTemplate().getConfig().getHtml().getPrefix());
		resolver.setSuffix(props.getTemplate().getConfig().getHtml().getSuffix());
		resolver.setCacheable(props.getTemplate().getConfig().getHtml().getCacheable());
		resolver.setOrder(HTML_CHAIN_ORDER);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	@Bean
	public ITemplateResolver cssTemplateResolver(
		final ApplicationProperties props
	) {
		log.info("Configurando template resolver para CSS: '{}'", props.getTemplate().getConfig().getCss());
		final var resolver = new SpringResourceTemplateResolver();
		resolver.setName("app-css-template-resolver");
		resolver.setCharacterEncoding(props.getTemplate().getConfig().getCss().getEncoding().name());
		resolver.setPrefix(props.getTemplate().getConfig().getCss().getPrefix());
		resolver.setSuffix(props.getTemplate().getConfig().getCss().getSuffix());
		resolver.setCacheable(props.getTemplate().getConfig().getHtml().getCacheable());
		resolver.setOrder(CSS_CHAIN_ORDER);
		resolver.setTemplateMode(TemplateMode.CSS);
		return resolver;
	}
}
