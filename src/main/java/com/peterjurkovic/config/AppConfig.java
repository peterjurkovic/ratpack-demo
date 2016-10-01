package com.peterjurkovic.config;

public class AppConfig {

	private String serviceUrl;
	private Database database;

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public static class Database {
		private String schema;
		private String username;
		private String password;
		private int port;

		public String getSchema() {
			return schema;
		}

		public void setSchema(String schema) {
			this.schema = schema;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

	}
}
