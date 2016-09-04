package com.peterjurkovic.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.module.ModuleDemo;
import com.zaxxer.hikari.HikariConfig;

import ratpack.exec.Blocking;
import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.server.RatpackServer;

public class DbApp {

	static Logger log = LoggerFactory.getLogger(ModuleDemo.class);

	public static void main(String[] args) throws Exception {
		RatpackServer.start(s -> s 
			 .serverConfig( configBuilder -> configBuilder
	             .findBaseDir()
	             .props("db.properties")
	             .require("/database", HikariConfig.class)
			 )
			 .registry(Guice.registry( bindings -> bindings
					 .module(HikariModule.class)
					 .bind(DatabaseInit.class)
			 ))
			 .handlers( chain -> chain
					 .post("set/:val", ctx ->
			            Blocking.get(() -> {
			            	String val = ctx.getPathTokens().get("val");
			            	log.info("Saving: " + val);
			              try (Connection connection = ctx.get(DataSource.class).getConnection()) {
			                PreparedStatement statement = connection.prepareStatement("insert into val (id, val) values (?, ?)");
			                statement.setInt(1, 1);
			                statement.setString(2, ctx.getPathTokens().get("val"));
			                return statement.executeUpdate();
			              }
			            }).then(result ->
			                ctx.render(result.toString())
			            )
			        )
			        .get("get", ctx ->
			            Blocking.get(() -> {
			              try (Connection connection = ctx.get(DataSource.class).getConnection()) {
			                PreparedStatement statement = connection.prepareStatement("select val from val where id = ?");
			                statement.setInt(1, 1);
			                ResultSet resultSet = statement.executeQuery();
			                resultSet.next();
			                return resultSet.getString(1);
			              }
			            }).then(ctx::render)
			        )	  
			 )
		); 
	}
}
