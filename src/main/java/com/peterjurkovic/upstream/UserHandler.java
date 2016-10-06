package com.peterjurkovic.upstream;

import static java.util.Objects.requireNonNull;

import java.net.URI;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.client.HttpClient;
import ratpack.http.client.ReceivedResponse;
import ratpack.jackson.Jackson;

public class UserHandler implements Handler{
	
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	
	@Inject
	private UserHandler(HttpClient httpClient, ObjectMapper objectMapper){
		this.httpClient = requireNonNull(httpClient);
		this.objectMapper = requireNonNull(objectMapper);
	}
	
	@Override
	public void handle(Context ctx) throws Exception {
		httpClient.get(new URI("http://localhost:9999/slow-endpoint"))
			.map(ReceivedResponse::getBody)
			.map(body -> objectMapper.readValue(body.getText(), User.class))
			.map(Jackson::json)
			.then(ctx::render);
	}

}
