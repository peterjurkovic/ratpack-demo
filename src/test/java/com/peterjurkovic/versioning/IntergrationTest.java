package com.peterjurkovic.versioning;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.peterjurkovic.common.UserService;

import ratpack.impose.ImpositionsSpec;
import ratpack.impose.UserRegistryImposition;
import ratpack.registry.Registry;
import ratpack.test.ApplicationUnderTest;
import ratpack.test.MainClassApplicationUnderTest;

@RunWith(MockitoJUnitRunner.class)
public class IntergrationTest {

	@Mock
	UserService mockUserService;
	
	ApplicationUnderTest aut = new MainClassApplicationUnderTest(Server.class){
		protected void addImpositions(ImpositionsSpec impositions) {
			impositions.add(UserRegistryImposition.of(
				Registry.of( r -> r.add(UserService.class, mockUserService))
			));
		};
	};
	
}
