package com.peterjurkovic.demo;

import static ratpack.jackson.Jackson.json;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.peterjurkovic.demo.user.User;

import io.netty.handler.codec.http.HttpMethod;
import ratpack.http.client.ReceivedResponse;
import ratpack.jackson.Jackson;
import ratpack.test.MainClassApplicationUnderTest;

public class AppTest {

	MainClassApplicationUnderTest aut = new MainClassApplicationUnderTest(App.class);

	@After
	public void tearDown() {
		aut.close();
	}

	@Test
	public void shouldReturnBrowserName(){
		String responseTest = aut.getHttpClient().requestSpec( s -> s
				.headers( headers -> headers.add("User-agent", "firefox"))
		).get("/browser").getBody().getText();
		
		Assert.assertEquals("Firefox", responseTest);
	}
	
	@Test
	public void shouldReturnListOfUsers(){
		ReceivedResponse res = aut.getHttpClient().get("api/user");
		
		Assert.assertEquals(200, res.getStatus().getCode());
	}
	
	@Test
	public void shouldSaveuser(){
		ReceivedResponse res = aut.getHttpClient()
				.request("api/user", spec -> spec
					.method("POST")
					.headers( h -> h.set("Content-type", "application/json"))
					.body(b -> b.text("{ \"name\":\"name\", \"email\":\"email\"}") )
				);
		
		Assert.assertEquals(201, res.getStatus().getCode());
	}
	
//	@Test
//	public void testUser(){
//		ReceivedResponse res = aut.getHttpClient().post("api/user");
//		
//		Assert.assertEquals(200, res.getStatus().getCode());
//	}
 

}
