package org.vfd.vee_tranx;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@EnableMongoRepositories("org.vfd.vee_tranx.repositories.db")
@SpringBootApplication
public class VeeTranxApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeeTranxApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(6);
		template.setRequestFactory(new HttpComponentsClientHttpRequestFactory(
				HttpClients.custom().setConnectionManager(connectionManager).build()));
		return template;
	}

}
