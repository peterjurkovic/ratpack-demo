package com.peterjurkovic.versioning;

import com.peterjurkovic.versioning.UserAgentVersioningHandler.ClientVersion;

import ratpack.server.RatpackServer;

public class Server {
	
	public static void main(String[] args) throws Exception {
		RatpackServer.start( serverSpec -> serverSpec
			.handlers( chain -> chain
				.all( new UserAgentVersioningHandler() )
				.get( ctx -> {
					ctx.render("Handling version: " + ctx.get(ClientVersion.class));
				})
			)
		);
	}

}
