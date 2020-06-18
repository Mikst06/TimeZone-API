package com.example.demo.other;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class TimeZonesList {
    public static List<String> returnTimeZoneList(RestTemplate restTemplate)
    {
            ResponseEntity<List<String>> zonesResponse =
                    restTemplate.exchange("http://worldtimeapi.org/api/timezone",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                            });

            return zonesResponse.getBody();

    }
}
