package com.peterjurkovic.db;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.module.ModuleDemo;

import ratpack.service.Service;
import ratpack.service.StartEvent;

public class DatabaseInit implements Service {

	static Logger log = LoggerFactory.getLogger(ModuleDemo.class);

	public void onStart(StartEvent startEvent) throws Exception {
		DataSource dataSource = startEvent.getRegistry().get(DataSource.class);
		log.info("DataSource: " + dataSource);
		try (Connection connection = dataSource.getConnection()) {
			log.info("Creating tables..." );
			connection.createStatement()
					.executeUpdate("create table if not exists val(ID INT PRIMARY KEY, val VARCHAR(255));");
		}
	}

}
