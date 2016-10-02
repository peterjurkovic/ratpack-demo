package com.peterjurkovic;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.config.ConfigExample;

import ratpack.exec.Blocking;
import ratpack.server.RatpackServer;

public class ExecutionModelExample {
	
	static Logger LOG = LoggerFactory.getLogger(ConfigExample.class); 
	
	static UserService userService = new UserService();
	
	static class UserService {
		List<Object> loadUsers(){
			return Collections.emptyList();
		}
	}
	
	public static void main(String[] args) throws Exception {
		RatpackServer.start( spec -> spec
			 .handlers( chain -> chain
				 .get( ctx -> {
					 print("1");
					 Blocking.get(() -> {
						 print("2");
						 return userService.loadUsers();
					 }).then( userList -> {
						 print("3");
						 ctx.render( userList );
					 });
					 print("4");
				 })		  
			 )
		);
	}

	private static void print(String string) {
		System.out.print(string);
		
	}
}
