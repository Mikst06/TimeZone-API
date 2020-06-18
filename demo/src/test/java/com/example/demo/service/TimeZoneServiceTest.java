package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeZoneServiceTest {

    private static final String LOCATION = "Europe/London";
    private static final String URI_AREA_LOCATION = "http://worldtimeapi.org/api/timezone/{areaLocation}";
    private static final String URI = "http://worldtimeapi.org/api/timezone";

    @Mock
    private RestTemplateWrapper restTemplateWrapper;

    @InjectMocks
    private TimeZoneService timeZoneService;

    @Test
    public void when_area_location_expect_restTemplate_called() {
        timeZoneService.getDatetime(LOCATION);
        verify(restTemplateWrapper).exchange(URI_AREA_LOCATION, LOCATION);
    }

    @Test
    public void when_zones_response_is_null_expect_empty_list_returned() {
        when(restTemplateWrapper.exchange(URI, HttpMethod.GET)).thenReturn(null);
        List<String> timeZones = timeZoneService.getTimeZones();
        verify(restTemplateWrapper).exchange(URI, HttpMethod.GET);
        assertThat(timeZones).isEqualTo(Collections.emptyList());
    }

    @Test
    public void when_zones_response_is_correct_expect_time_zones_list_returned() {
        List<String> timeZones = new ArrayList<>();
        timeZones.add("America/Juneau");
        timeZones.add("America/Indiana/Winamac");
        timeZones.add("Europe/Chisinau");
        ResponseEntity<List<String>> responseEntity = new ResponseEntity(timeZones, HttpStatus.ACCEPTED);

        when(restTemplateWrapper.exchange(URI, HttpMethod.GET)).thenReturn(responseEntity);
        List<String> respondedTimeZones = timeZoneService.getTimeZones();
        assertThat(respondedTimeZones.size()).isEqualTo(3);
    }

}