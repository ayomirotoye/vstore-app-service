package org.vfd.vee_tranx;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.vfd.vee_tranx.controllers.IndexingController;
import org.vfd.vee_tranx.models.IndexDetailsData;

@EnableAsync
@EnableMongoRepositories("org.vfd.vee_tranx.repositories.db")
@SpringBootApplication
public class VeeTranxApplication   {
	
	 @Autowired
	  private IndexingController controller;

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
	
	
//	 @Override
//	  public void run(String... args) throws Exception {
//	    for (int i = 0; i < 10; ++i) {
//	      IndexDetailsData dt = IndexDetailsData
//	        .builder()
//	        .id(randomStrings())
//	        .tag("TRANSACTIONS")
//	        .data(buildData())
//	        .build();
//	      CompletableFuture.supplyAsync(
//	        () -> controller.storeDocs(Collections.singletonList(dt))
//	      );
//	    }
//	  }

	  public HashMap<String, String> buildData() {
	    HashMap<String, String> request = new HashMap<>();
	    for (int i = 0; i < 10; ++i) {
	      request.put(randomStrings(), randomStrings());
	    }
		request.put("narration", randomStrings());
	    return request;
	  }

	  public String randomStrings() {
	    int length = 10;
	    boolean useLetters = true;
	    boolean useNumbers = false;
	    String generatedString = RandomStringUtils.random(
	      length,
	      useLetters,
	      useNumbers
	    );

	    return generatedString;
	  }

}
