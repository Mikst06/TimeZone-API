package com.example.demo.main;

import com.example.demo.other.Timezone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {

		return args -> {

			try{
				ResponseEntity<List<String>> zonesResponse =
						restTemplate.exchange("http://worldtimeapi.org/api/timezone",
								HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
								});

				List<String> timeZones = zonesResponse.getBody();

				String areaLocation = "Europe/London";
				String uri = "http://worldtimeapi.org/api/timezone/{areaLocation}";

				Map<String, String> pathVariableMap = new HashMap<>();
				pathVariableMap.put("areaLocation", areaLocation);
				String uriString = UriComponentsBuilder.fromUriString(uri).buildAndExpand(pathVariableMap)
						.toUriString();

				try{
					Timezone timezone = restTemplate.getForObject(uriString, Timezone.class);
					System.out.print("\n\n\nTime for area: \t" + timezone.getDateTime());
				}
				catch (HttpClientErrorException exc) {
					log.info("HttpStatusCodeException: {}",exc.getStatusCode());
					for (String t:timeZones) {
						System.out.println(t);
					}
				}
				catch (HttpServerErrorException exc) {
					log.info("HttpStatusCodeException: {}",exc.getStatusCode());
				}
			}
			catch (HttpServerErrorException exc) {
				log.info("HttpStatusCodeException: {}",exc.getStatusCode());
			}



		};
	}

}