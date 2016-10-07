package com.peterjurkovic.config;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static ratpack.jackson.Jackson.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.test.embed.EmbeddedApp;

public class ConfigExample {

	static Logger LOG = LoggerFactory.getLogger(ConfigExample.class); 
		
	public static void main(String[] args) throws Exception {
		EmbeddedApp.of(s -> s 
			 .serverConfig( configBuilder -> configBuilder
	             .findBaseDir()
	             .props("config.properties")
	             .sysProps()
	             .yaml("config.yml")
	             .env()
	             .require("", AppConfig.class)
			 )
			 .handlers( chain -> chain
				 .get("config", ctx -> {
					 ctx.render( json(ctx.get(AppConfig.class)));
				 })		  
			 )
		).test( httpClient -> {
		     String response = httpClient.get("/config").getBody().getText();
		     
		     LOG.debug( response );
		     
		     assertThatJson( response )
			     .node("serviceUrl").isEqualTo("http://localhost:1234")
			     .node("database.schema").isEqualTo("schema-properties")
			     .node("database.username").isEqualTo("username-yml")
			     .node("database.password").isEqualTo("password-env")
			     .node("database.port").isEqualTo( 5555 );
		   });
	}
}
