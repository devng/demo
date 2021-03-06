package com.devng.spark.service;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).to(UserServiceInMemoryImpl.class).in(Singleton.class);
	}
}
