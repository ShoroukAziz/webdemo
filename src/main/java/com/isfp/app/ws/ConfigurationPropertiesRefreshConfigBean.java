package com.isfp.app.ws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@RefreshScope
public class ConfigurationPropertiesRefreshConfigBean {
	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

}