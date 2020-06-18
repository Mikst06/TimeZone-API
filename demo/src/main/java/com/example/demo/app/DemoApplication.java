package com.example.demo.app;

import com.example.demo.other.TimeZonesList;
import com.example.demo.other.Timezone;

import java.util.List;

import com.example.demo.other.TimezoneRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
				List<String> timeZones = TimeZonesList.returnTimeZoneList(restTemplate);

				String areaLocation = "Europe/London";

				try{
					Timezone timezone = TimezoneRest.returnTimeZoneList(restTemplate, areaLocation);
					System.out.format("\n\n\nTime for area: \t%s",timezone.getDateTime());}
				catch (HttpClientErrorException exc) {
					System.out.println();
					System.out.format("\nHttpStatusCodeException: %s\n\n",exc.getStatusCode());

					for (String t:timeZones) {
						System.out.println(t);
					}
				}
				catch (HttpServerErrorException exc) {
					System.out.println();
					System.out.format("\nHttpStatusCodeException: %s",exc.getStatusCode());
				}
				catch (RestClientException exc) {
					System.out.println();
					log.info("WRONG INPUT");

					for (String t:timeZones) {
						System.out.println(t);
					}
				}
			}
			catch (HttpServerErrorException exc) {
				System.out.println();
				System.out.format("\nHttpStatusCodeException: %s",exc.getStatusCode());
			}

			System.out.println();
		};
	}

}