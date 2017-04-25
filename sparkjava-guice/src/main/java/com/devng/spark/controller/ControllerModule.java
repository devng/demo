package com.devng.spark.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ErrorController.class).in(Singleton.class);
		bind(UserController.class).in(Singleton.class);
	}
}
