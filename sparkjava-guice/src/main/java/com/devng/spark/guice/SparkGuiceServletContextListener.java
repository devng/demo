package com.devng.spark.guice;

import com.devng.spark.MainModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import java.util.Arrays;

public class SparkGuiceServletContextListener extends GuiceServletContextListener {

	private Injector injector = null;

	@Override
	protected Injector getInjector() {
		if (injector == null) {
			// We need to install the Guice Servlet module as well to get all the servlet scopes, etc.
			// See https://github.com/google/guice/wiki/ServletModule
			injector = Guice.createInjector(Arrays.asList(new ServletModule(), new MainModule()));
		}
		return injector;
	}
}
