package com.peterjurkovic.upstream;

import static com.peterjurkovic.upstream.handler.UserAgentVersioningHandler.ClientVersion;

import com.peterjurkovic.upstream.handler.HelloNexmoHandler;
import com.peterjurkovic.upstream.handler.UserAgentVersioningHandler;

import ratpack.server.RatpackServer;

public class Server {
	
	public static void main(String[] args) throws Exception {
		RatpackServer.start( serverSpec -> serverSpec
				.handlers( chain -> chain
					.path("hello", new HelloNexmoHandler())
					.prefix("version", action -> action
						.all(new UserAgentVersioningHandler())
						.get(ctx -> {
							ctx.render( "Handling API version: " + ctx.get(ClientVersion.class));
						})
					)
					.prefix("user", action -> action
						.get(ctx -> {
							ctx.render( "Hello Nexmo!" );
						}
					)
				)
			) 
		);
	}
	
}
