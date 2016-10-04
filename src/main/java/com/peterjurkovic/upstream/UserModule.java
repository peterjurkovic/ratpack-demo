package com.peterjurkovic.upstream;

import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(UserHandler.class);
	}

}
