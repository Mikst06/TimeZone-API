package com.example.demo;

import com.example.demo.model.Timezone;

import java.sql.Time;
import java.util.List;

import com.example.demo.service.TimeZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.lang.System.*;

@SpringBootApplication
public class DemoApplication {
	private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args).close();
	}

	@Bean
	public CommandLineRunner run(TimeZoneService timeZoneService) {

		return args -> {
				List<String> timeZones = timeZoneService.getTimeZones();
				try {
					String areaLocation = args[0];
					try {
						Timezone timezone = timeZoneService.getDatetime(areaLocation);
						out.print("\n\n\n--------------------------------------------------------------------------------\n");
						out.format("-------------> Time for area:    %s <-------------\n", timezone.getDateTime());
						out.print("--------------------------------------------------------------------------------\n\n\n");
						out.println();
					} catch (Exception e) {
						timeZones.forEach(out::println);
						LOG.error("Exception occured due to inproper timezone input: {}", args[0]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					timeZones.forEach(out::println);
					LOG.error("Exception occured due to no input");
				}
		};
	}

}