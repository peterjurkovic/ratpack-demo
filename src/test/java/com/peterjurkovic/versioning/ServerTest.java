package com.peterjurkovic.versioning;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import ratpack.test.ApplicationUnderTest;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.http.TestHttpClient;

public class ServerTest {

	ApplicationUnderTest aut = new MainClassApplicationUnderTest(Server.class);

	@Test
	public void test() {
		TestHttpClient httpClient = aut.getHttpClient()
				.requestSpec(spec -> spec.getHeaders().set("User-Agent", "Microservice v2.0"));
		String responseText = httpClient.get().getBody().getText();
		Assertions.assertThat(responseText).isEqualTo("Handling version: V2");
	}

}
