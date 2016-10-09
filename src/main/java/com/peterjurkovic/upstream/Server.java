package com.peterjurkovic.upstream;

import ratpack.guice.Guice;
import ratpack.server.RatpackServer;

public class Server {

	public static void main(String[] args) throws Exception {
		RatpackServer.start( serverSpec -> serverSpec
			.serverConfig( config -> config.threads( 1 ))	
			.registry(Guice.registry( b -> b.module( UserModule.class )))	
			.handlers( chain -> chain
				.get(UserHandler.class)
			)
		);
	}
	
}
