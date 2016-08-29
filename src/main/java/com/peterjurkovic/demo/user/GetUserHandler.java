package com.peterjurkovic.demo.user;

import static ratpack.jackson.Jackson.json;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class GetUserHandler implements Handler{

	Logger log = LoggerFactory.getLogger(getClass());
	
	private UserService userService;
	
	@Inject
	public GetUserHandler(UserService userService){
		this.userService = userService;
	}
	
	@Override
	public void handle(Context ctx) throws Exception {
		log.info("Handling user GET");
		userService.getUsers().then( users -> {
			ctx.render( json(users) );
		});
	}

	
}
