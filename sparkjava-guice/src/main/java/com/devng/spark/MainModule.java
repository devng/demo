package com.devng.spark;

import javax.inject.Named;
import javax.inject.Singleton;

import com.devng.spark.controller.ControllerModule;
import com.devng.spark.service.ServiceModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MainModule extends AbstractModule {

	@Provides
	@Singleton
	@Named("hello-message")
	public String helloMessage() {
		return "Hello from Spark using Guice.";
	}

	@Override
	protected void configure() {
		install(new ServiceModule());
		install(new ControllerModule());
	}
}
