package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.ClientInputException;
import com.example.demo.exceptions.InternalServerException;
import com.example.demo.model.Timezone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Component
public class RestTemplateWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    private final RestTemplate restTemplate;

    public RestTemplateWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<String>> exchange(String URI, HttpMethod httpMethod) {
        try {
            return restTemplate.exchange(URI,
                    httpMethod, null, new ParameterizedTypeReference<List<String>>() {
                    });
        }
        catch (HttpServerErrorException exc) {
            System.out.println();
            LOG.info("\nHttpStatusCodeException: {}",exc.getStatusCode());

            throw new InternalServerException();
        }
    }

    public Timezone getForObject(String URI_AREA_LOCATION, String areaLocation, List<String> timezonesList) {
        try {
            Map<String, String> pathVariableMap = new HashMap<>();
            pathVariableMap.put("areaLocation", areaLocation);
            String uriString = UriComponentsBuilder.fromUriString(URI_AREA_LOCATION).buildAndExpand(pathVariableMap)
                    .toUriString();

            return restTemplate.getForObject(uriString, Timezone.class);
        }
        catch (HttpClientErrorException exc) {
            System.out.println();
            LOG.info("\nHttpStatusCodeException: {}\n\n",exc.getStatusCode());

            for (String t:timezonesList) {
                System.out.println(t);
            }

            throw new ClientInputException();
        }
        catch (HttpServerErrorException exc) {
            System.out.println();
            LOG.info("\nHttpStatusCodeException: {}",exc.getStatusCode());

            throw new InternalServerException();
        }
        catch (RestClientException exc) {
            System.out.println();
            LOG.info("\nHttpStatusCodeException: RestClientException");

            for (String t:timezonesList) {
                System.out.println(t);
            }

            throw new ClientInputException();
        }
    }


}
