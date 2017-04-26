package com.devng.spark.guice;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import com.google.inject.Injector;

import spark.servlet.SparkApplication;
import spark.servlet.SparkFilter;

public class SparkGuiceFilter extends SparkFilter {

	private static final String INJECTOR_NAME = Injector.class.getName();

	@Override
	protected SparkApplication[] getApplications(final FilterConfig filterConfig) throws ServletException {
		final SparkApplication[] applications = super.getApplications(filterConfig);

		final Injector injector = (Injector) filterConfig.getServletContext().getAttribute(INJECTOR_NAME);

		if (applications != null) {
			for (SparkApplication application : applications) {
				injector.injectMembers(application);
			}
		}

		return applications;
	}
}
