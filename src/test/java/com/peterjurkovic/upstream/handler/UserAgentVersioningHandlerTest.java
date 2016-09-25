package com.peterjurkovic.upstream.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.peterjurkovic.upstream.handler.UserAgentVersioningHandler.ClientVersion;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

@RunWith(JUnitParamsRunner.class)
public class UserAgentVersioningHandlerTest {

	private UserAgentVersioningHandler handler = new UserAgentVersioningHandler();
	
	@Test
	@Parameters({
		"Microservice v1.0 | V1",
		"Microservice v2.0 | V2",
		"Microservice v3.0 | V3"
	})
	public void shouldRegisterClientVersionBasedOnUserAgent(String actualUserAgent, ClientVersion expectedVersion) throws Exception{
		HandlingResult result = RequestFixture.handle(handler, fixture -> fixture.header("User-Agent", actualUserAgent));
		
		assertThat(result.getRegistry().get(ClientVersion.class)).isEqualTo(expectedVersion);
		assertThat(result.isCalledNext()).isTrue();
	}
}
