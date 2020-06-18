package com.example.demo.other;
import com.example.demo.other.Timezone;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TimezoneRest {
    public static Timezone returnTimeZoneList(RestTemplate restTemplate, String areaLocation){
        String uri = "http://worldtimeapi.org/api/timezone/{areaLocation}";

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put("areaLocation", areaLocation);
        String uriString = UriComponentsBuilder.fromUriString(uri).buildAndExpand(pathVariableMap)
                .toUriString();

        return restTemplate.getForObject(uriString, Timezone.class);
    }
}
