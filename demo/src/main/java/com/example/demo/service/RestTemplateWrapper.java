package com.example.demo.service;

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

@Component
public class RestTemplateWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateWrapper.class);
    private static final String HTTP_STATUS_CODE_EXCEPTION = "HttpStatusCodeException: {}";

    private final RestTemplate restTemplate;

    public RestTemplateWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<String>> exchange(String uri, HttpMethod httpMethod) {
        try {
            return restTemplate.exchange(uri,
                    httpMethod, null, new ParameterizedTypeReference<List<String>>() {});
        } catch (HttpServerErrorException exc) {
            LOG.info(HTTP_STATUS_CODE_EXCEPTION, exc.getStatusCode());

            throw new InternalServerException();
        }
    }

    public Timezone exchange(String uriAreaLocation, String areaLocation) {
        try {
            Map<String, String> pathVariableMap = new HashMap<>();
            pathVariableMap.put("areaLocation", areaLocation);
            String uriString = UriComponentsBuilder.fromUriString(uriAreaLocation).buildAndExpand(pathVariableMap)
                    .toUriString();

            return restTemplate.getForObject(uriString, Timezone.class);
        } catch (HttpClientErrorException exc) {
            LOG.info(HTTP_STATUS_CODE_EXCEPTION, exc.getStatusCode());

            throw new ClientInputException("Invalid or missing timezone detected", exc);
        } catch (HttpServerErrorException exc) {
            LOG.info(HTTP_STATUS_CODE_EXCEPTION, exc.getStatusCode());

            throw new InternalServerException();
        } catch (RestClientException exc) {
            LOG.info("HttpStatusCodeException: RestClientException");

            throw new ClientInputException("Invalid or missing timezone detected", exc);
        }
    }


}
