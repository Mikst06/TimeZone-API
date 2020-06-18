package com.example.demo.service;

import com.example.demo.model.Timezone;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeZoneService {

    private static final String URI = "http://worldtimeapi.org/api/timezone";

    private static final String URI_AREA_LOCATION = "http://worldtimeapi.org/api/timezone/{areaLocation}";

    private RestTemplateWrapper restTemplateWrapper;

    public TimeZoneService(RestTemplateWrapper restTemplateWrapper) {
        this.restTemplateWrapper = restTemplateWrapper;
    }

    public Timezone returnTimeZoneList(String areaLocation, List<String> timezoneList) {

        return restTemplateWrapper.getForObject(URI_AREA_LOCATION, areaLocation, timezoneList);
    }

    public List<String> returnTimeZoneList() {
        ResponseEntity<List<String>> zonesResponse = restTemplateWrapper.exchange(URI,HttpMethod.GET);

        return zonesResponse.getBody();

    }
}
