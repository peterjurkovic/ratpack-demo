package com.peterjurkovic.demo.user;

import ratpack.exec.Promise;

public interface UserService {

	Promise<User> save(User user);

	Promise<User> loadByUsername(String username);
	
	Promise<UserProfile> loadUserProfile(User username);
}
