package com.example.demo;

import com.example.demo.model.Timezone;

import java.util.List;

import com.example.demo.service.TimeZoneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args).close();
	}

	@Bean
	public CommandLineRunner run(TimeZoneService timeZoneService) {

		return args -> {
				List<String> timeZones = timeZoneService.returnTimeZoneList();
				String areaLocation = args[0];
				//String areaLocation = "aEurope/London";
				Timezone timezone = timeZoneService.returnTimeZoneList(areaLocation, timeZones);
				System.out.print("\n\n\n--------------------------------------------------------------------------------\n");
				System.out.format("-------------> Time for area:    %s <-------------\n", timezone.getDateTime());
				System.out.print("--------------------------------------------------------------------------------\n\n\n");
				System.out.println();
		};
	}

}