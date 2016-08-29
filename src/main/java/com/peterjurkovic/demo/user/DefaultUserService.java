package com.peterjurkovic.demo.user;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.exec.Promise;

public class DefaultUserService implements UserService {
	
	private final List<User> storage = new ArrayList<>();
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	public DefaultUserService(){
		log.info("UserService has been created");
	}

	@Override
	public Promise<User> save(User user) {
		storage.add(user);
		
		return Promise.sync( () -> null);
	}

	@Override
	public Promise<List<User>> getUsers() {
		return Promise.sync( () -> storage);
	}
	
	
}
