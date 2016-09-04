package com.peterjurkovic.demo.user;

import static ratpack.jackson.Jackson.json;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class GetUserHandler implements Handler{

	Logger log = LoggerFactory.getLogger(getClass());
	
	private UserService userService;
	private BlockingDbService blockingDbService;
	
	@Inject
	public GetUserHandler(UserService userService, BlockingDbService blockingDbService){
		this.userService = userService;
		this.blockingDbService = blockingDbService;
	}
	
	@Override
	public void handle(Context ctx) throws Exception {
		log.info("Handling user GET");
		String username = ctx.getPathTokens().get("username");
		
		Container c = new Container();
		log.info(" ok loading USER by " + username);
		Blocking.get(() -> blockingDbService.loadByUsername(username)).then( user ->  c.user = user);
		
		log.info(" user should be populated in " + c + " loading prifle");
		Blocking.get(() -> blockingDbService.loadProfile(c.user)).then( profile ->  c.profile = profile);
		log.info(" all done, rendering " +c );
		ctx.render( json( c ) );
	}

	static class Container{
		User user;
		UserProfile profile;
		@Override
		public String toString() {
			return "Container [user=" + user + ", profile=" + profile + "]";
		}
		
		
	}
	
}
