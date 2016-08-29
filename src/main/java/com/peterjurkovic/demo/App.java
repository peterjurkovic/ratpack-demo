package com.peterjurkovic.demo;

import static ratpack.jackson.Jackson.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.demo.user.DownstreamUserService;
import com.peterjurkovic.demo.user.UserModule;

import ratpack.guice.Guice;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class App {

	static Logger log = LoggerFactory.getLogger(App.class); 
	
	public static void main(String[] args) throws Exception {
		RatpackServer.start(s -> s 
			 .serverConfig( configBuilder -> configBuilder
	             .port(5050)
	             .baseDir(BaseDir.find())
	             .props("config.properties")
	             .env()
	             .require("/downstream", Config.class)
			 )
			 .registry(Guice.registry(bindings -> bindings.module(UserModule.class)) )
			 .handlers( chain -> chain
				 .get("user", c -> {
					 DownstreamUserService service = c.get(DownstreamUserService.class);
					 service.load().then( user -> c.render( json(user) ));
				 })	
			 )
		);
	}
}
