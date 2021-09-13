package com.isfp.app.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Autowired
	private Environment env;

	public String getTokenSecret() {
		return env.getProperty("tokenSecret");
	}

	public String getMessage() {
		return env.getProperty("message");
	}

	public String getUrl() {
		return env.getProperty("spring.datasource.url");
	}
}
