package com.peterjurkovic.common;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.upstream.User;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;

public class PostUserHandler implements Handler{
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	UserService userService;
	
	@Inject
	public PostUserHandler(UserService userService) {
		log.info("PostUserHandler created with "+ userService);
		this.userService = userService;
	}
	

	@Override
	public void handle(Context ctx) throws Exception {
		log.info("Handling user POST");
		ctx.parse(Jackson.fromJson( User.class ) )
			.flatMap( userService::save )
			.then( user -> ctx.getResponse().status(201).send());

		
	}
}
