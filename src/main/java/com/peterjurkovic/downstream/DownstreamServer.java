package com.peterjurkovic.downstream;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Json;

public class DownstreamServer {

	 private WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(9999));
	 
	 private final static String USER_JSON = Json.write(new User("Peter", "peter.jurkovic@nexmo.com"));

    public void start() {
        wireMockServer.stubFor(get(urlEqualTo("/ping"))
        		.willReturn(aResponse()
				.withBody("DATA")
				.withFixedDelay(100)));
        
        wireMockServer.stubFor(get(urlEqualTo("/slow-endpoint"))
        		.willReturn(
        				aResponse()
        				.withHeader("Content-Type", "application/json")
        				.withHeader("Content-Length", String.valueOf(USER_JSON.length()))
	    				.withBody( USER_JSON )
	    				.withFixedDelay(3000)
    		));

        wireMockServer.start();
        System.out.println("DownstreamServer is running...");
        
    }

     public static void main(String[] args) {
        new DownstreamServer().start();
    }

     static class User{
    	 String name;
    	 String email;
    	 
    	 
		public User(String name, String email) {
			super();
			this.name = name;
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
    	 
    	 
     }
}
