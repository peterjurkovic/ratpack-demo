package com.peterjurkovic.common;

import com.peterjurkovic.upstream.User;

public class BlockingDbService {

	User loadByUsername(String username){
		return new User(username, username + "@email.com");
	}
	
	UserProfile loadProfile(User user){
		return new UserProfile("info of " + user);
	}
}
