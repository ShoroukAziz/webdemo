package com.isfp.app.ws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "spring.datasource")
@RefreshScope
public class ConfigurationPropertiesRefreshConfigBean {
	private String url;
//
//	public void setUrl(String url) {
//		this.url = url;
//	}

	public ConfigurationPropertiesRefreshConfigBean(@Value("${spring.datasource.url}") String url) {
		this.url = url;
	}

}