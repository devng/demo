package com.devng.spark.guice;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import com.devng.spark.MainModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import spark.servlet.SparkApplication;
import spark.servlet.SparkFilter;

public class SparkGuiceFilter extends SparkFilter {

	private Injector injector = null;

	@Override
	protected SparkApplication[] getApplications(final FilterConfig filterConfig) throws ServletException {
		final SparkApplication[] applications = super.getApplications(filterConfig);

		if (this.injector == null) {
			this.injector = Guice.createInjector(new MainModule());
		}

		if (applications != null && applications.length != 0) {
			for (SparkApplication application : applications) {
				this.injector.injectMembers(application);
			}
		}

		return applications;
	}
}
