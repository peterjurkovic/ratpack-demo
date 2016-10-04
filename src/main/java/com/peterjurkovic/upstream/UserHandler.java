package com.peterjurkovic.upstream;

import static ratpack.jackson.Jackson.json;

import java.net.URI;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.client.HttpClient;

public class UserHandler implements Handler{
	
	private final static Logger LOG = LoggerFactory.getLogger(UserHandler.class);

	
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	
	@Inject
	private UserHandler(HttpClient httpClient, ObjectMapper objectMapper){
		this.httpClient = httpClient;
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void handle(Context ctx) throws Exception {
		httpClient.get(new URI("http://localhost:9999/slow-endpoint"))
			.map( res -> objectMapper.readValue(res.getBody().getInputStream(), User.class))
			.then( user -> {
				LOG.info("Sending user back to a client " + user);
				ctx.render( json( user ));
			});
	}

}
