package org.vfd.vee_tranx.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class AppProperties {
	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@Value("${spring.data.mongodb.database}")
	private String databaseName;
	
	@Value("${lookup.url}")
	private String lookupUrl;
}
