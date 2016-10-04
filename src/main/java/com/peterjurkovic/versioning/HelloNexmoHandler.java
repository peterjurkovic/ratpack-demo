package com.peterjurkovic.versioning;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class HelloNexmoHandler implements Handler{

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.render("Hello Nexmo!");
	}

}
