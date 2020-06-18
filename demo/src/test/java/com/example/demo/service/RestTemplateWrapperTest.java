package com.example.demo.service;

import com.example.demo.exceptions.ClientInputException;
import com.example.demo.exceptions.InternalServerException;
import com.example.demo.model.Timezone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestTemplateWrapperTest {

    private static final String URI = "http://localhost:1234";
    private static final String LOCATION_URI = "http://localhost:1234/{areaLocation}";
    private static final String EXPANDED_LOCATION_URI = "http://localhost:1234/Europe/London";
    private static final String AREA_LOCATION = "Europe/London";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestTemplateWrapper restTemplateWrapper;

    @Test
    public void when_HttpServerErrorException_occured_then_InternalServerException() {
        when(restTemplate.exchange(eq(URI), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
                .thenThrow(HttpServerErrorException.class);

        Assertions.assertThrows(InternalServerException.class, () -> restTemplateWrapper.exchange(URI, HttpMethod.GET));
    }

    @Test
    public void when_HttpClientErrorException_occured_then_ClientInputException() {
        when(restTemplate.getForObject(eq(EXPANDED_LOCATION_URI), eq(Timezone.class)))
                .thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(ClientInputException.class, () -> restTemplateWrapper.exchange(LOCATION_URI, AREA_LOCATION));
    }

    @Test
    public void when_Get_Timezone_then_HttpServerErrorException_expect_InternalServerException() {
        when(restTemplate.getForObject(eq(EXPANDED_LOCATION_URI), eq(Timezone.class)))
                .thenThrow(HttpServerErrorException.class);

        Assertions.assertThrows(InternalServerException.class, () -> restTemplateWrapper.exchange(LOCATION_URI, AREA_LOCATION));
    }


}