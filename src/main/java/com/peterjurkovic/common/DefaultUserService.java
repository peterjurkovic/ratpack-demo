package com.peterjurkovic.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.upstream.User;

import ratpack.exec.Blocking;
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
	public Promise<User> loadByUsername(String username) {
		return Promise.sync( () -> {
			log.info("loading " + username);
			return new User(username, username + "@email");
		});
	}

	@Override
	public Promise<UserProfile> loadUserProfile(User user) {
		return Blocking.get( () -> {
			log.info("loading PROFILE " + user);
			return new UserProfile("userinfo of ");
		});
	}

}
