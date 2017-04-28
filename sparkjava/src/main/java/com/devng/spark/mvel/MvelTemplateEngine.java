package com.devng.spark.mvel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import spark.ModelAndView;
import spark.TemplateEngine;

public class MvelTemplateEngine extends TemplateEngine {

	private static final String DEFAULT_PREFIX = "templates/";

	private static final String DEFAULT_SUFFIX = ".html";

	// lets do some caching of expressions to see if we cant go a bit faster
	private final ConcurrentMap<String, CompiledTemplate> templateCache;

	private final String prefix;

	private final String suffix;

	public MvelTemplateEngine() {
		this(DEFAULT_PREFIX, DEFAULT_SUFFIX);
	}

	public MvelTemplateEngine(final String prefix, final String suffix) {
		this.templateCache = new ConcurrentHashMap<>();
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public String render(final ModelAndView modelAndView) {
		final CompiledTemplate compiledTemplate = loadTemplate(modelAndView.getViewName());
		return (String) TemplateRuntime.execute(compiledTemplate, modelAndView.getModel());
	}

	public CompiledTemplate loadTemplate(String resourceName) {
		// use the Stupid scanner trick - https://community.oracle.com/blogs/pat/2004/10/23/stupid-scanner-tricks
		final String fullPath = prefix + resourceName + suffix;
		CompiledTemplate compiledTemplate = templateCache.get(fullPath);
		if (compiledTemplate == null) {
			compiledTemplate = TemplateCompiler
					.compileTemplate(Thread.currentThread().getContextClassLoader().getResourceAsStream(fullPath));
			templateCache.putIfAbsent(fullPath, compiledTemplate);
		}
		return compiledTemplate;
	}
}
