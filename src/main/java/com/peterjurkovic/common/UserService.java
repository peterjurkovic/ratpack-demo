package com.peterjurkovic.common;

import com.peterjurkovic.upstream.User;

import ratpack.exec.Promise;

public interface UserService {

	Promise<User> save(User user);

	Promise<User> loadByUsername(String username);
	
	Promise<UserProfile> loadUserProfile(User username);
}
