package com.isfp.app.ws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

		@Bean
		public Docket apiDocket() {
			
			Docket docket = new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.isfp.app.ws"))
					.paths(PathSelectors.any())
					.build();
			
			return docket;
		}
		
		 @Bean
		    public LinkDiscoverers discoverers() {
		        List<LinkDiscoverer> plugins = new ArrayList<>();
		        plugins.add(new CollectionJsonLinkDiscoverer());
		        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

		 }
		
}
