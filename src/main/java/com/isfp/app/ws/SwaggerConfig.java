package com.isfp.app.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;

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
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	
	  Contact contact = new Contact(
	            "Shorouk Abdelaziz",
	            "https://shorouk.dev", 
	            "contact@shorouk.dev"
	    );
	
	  //TODO: SEARCH
	 List<VendorExtension> vendorExtensions = new ArrayList<>();
		
		ApiInfo apiInfo = new ApiInfo(
				"Photo app RESTful Web Service documentation",
				"This pages documents Photo app RESTful Web Service endpoints", 
				"1.0",
				"https://shorouk.dev", 
				contact, 
				"Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0", 
				vendorExtensions);

		@Bean
		public Docket apiDocket() {
			
			Docket docket = new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(apiInfo)
					.protocols(new HashSet<>(Arrays.asList("HTTP","HTTPs")))
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
