package com.peterjurkovic.demo.user;

import java.util.List;

import ratpack.exec.Promise;

public interface UserService {

	Promise<User> save(User user);

	Promise<List<User>> getUsers();
}
