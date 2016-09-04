package com.peterjurkovic.module;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static ratpack.jackson.Jackson.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.config.Settings;

import ratpack.test.embed.EmbeddedApp;

public class ModuleDemo {
	
	static Logger log = LoggerFactory.getLogger(ModuleDemo.class);

	public static void main(String[] args) throws Exception {
		EmbeddedApp.of(s -> s 
			 .serverConfig( configBuilder -> configBuilder
	             .findBaseDir()
	             .props("app.properties")
	             .sysProps()
	             .env()
	             .yaml("app.yml")
	             .require("/database", Settings.class)
			 )
			 .handlers( chain -> chain
				 .get("config", ctx -> {
					 ctx.render( json(ctx.get(Settings.class)));
				 })		  
			 )
		).test( httpClient -> {
		     String response = httpClient.get("/config").getBody().getText();
		     
		     log.debug( response );
		     
		     assertThatJson( response )
			     .node("name").isEqualTo("yamlname")
			     .node("host").isEqualTo("envhost")
			     .node("username").isEqualTo("dbusername")
			     .node("password").isEqualTo("dbpass");
		   });
	}
}
