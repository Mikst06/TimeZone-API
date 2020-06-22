package com.example.demo.service;

import com.example.demo.model.Timezone;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TimeZoneService {

    private static final String URI = "http://worldtimeapi.org/api/timezone";
    private static final String URI_AREA_LOCATION = "http://worldtimeapi.org/api/timezone/{areaLocation}";

    private final RestTemplateWrapper restTemplateWrapper;

    public TimeZoneService(RestTemplateWrapper restTemplateWrapper) { this.restTemplateWrapper = restTemplateWrapper; }

    public Timezone getDatetime(String areaLocation) {
        return restTemplateWrapper.exchange(URI_AREA_LOCATION, areaLocation);
    }

    public List<String> getTimeZones() {
        ResponseEntity<List<String>> zonesResponse = restTemplateWrapper.exchange(URI, HttpMethod.GET);

        if (zonesResponse != null && zonesResponse.getBody() != null) {
            return zonesResponse.getBody();
        } else {
            return Collections.emptyList();
        }
    }
}
