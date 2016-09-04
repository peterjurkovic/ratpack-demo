package com.peterjurkovic.demo.user;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peterjurkovic.demo.Config;

import ratpack.exec.Promise;
import ratpack.http.client.HttpClient;

public class DownstreamUserService {

	Logger log = LoggerFactory.getLogger(DownstreamUserService.class);
	
	private HttpClient httpClient;
	private ObjectMapper mapper;
	private URI slowUserUri;
	private URI fastUserUri;
	
	@Inject
	public DownstreamUserService(HttpClient httpClient, Config config, ObjectMapper mapper) {
		this.httpClient = httpClient;
		this.mapper = mapper;
		try {
			slowUserUri = new URI("http://" + config.getHost() + ":" + config.getPort() + "/slow-endpoint");
			fastUserUri = new URI("http://" + config.getHost() + ":" + config.getPort() + "/fast-endpoint");
		} catch (URISyntaxException e) {
			log.error("",e);
			throw new RuntimeException(e);
		}
	}
	
	public Promise<User> loadSlowUser(){
		return load( slowUserUri );
	}
	
	public Promise<User> loadFastUser(){
		return load( fastUserUri );
	}
	
	public Promise<User> load(URI uri){
		return httpClient.get( uri )
				.onError(e -> log.info("Error",e))
				.map( res -> mapper.readValue(res.getBody().getBytes(), User.class));		
	}
	
}
