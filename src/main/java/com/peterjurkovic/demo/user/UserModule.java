package com.peterjurkovic.demo.user;

import com.google.inject.AbstractModule;

public class UserModule  extends AbstractModule{

	@Override
	protected void configure() {
		bind(UserService.class).toInstance(new DefaultUserService());
		bind(DownstreamUserService.class);
		bind(PostUserHandler.class);
		bind(GetUserHandler.class);
		bind(BlockingDbService.class);
	}

}
