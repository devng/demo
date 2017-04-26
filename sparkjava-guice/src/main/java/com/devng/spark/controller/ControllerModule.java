package com.devng.spark.controller;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class ControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ErrorController.class).in(Singleton.class);
		bind(UserController.class).in(Singleton.class);
		bind(RequestInfoController.class).in(Singleton.class);
	}
}
