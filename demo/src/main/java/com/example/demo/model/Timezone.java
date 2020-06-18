package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Timezone {

    @JsonProperty("abbreviation")
    private String abbreviation;

    @JsonProperty("client_ip")
    private String clientIp;

    @JsonProperty("datetime")
    private String dateTime;

    @JsonProperty("day_of_week")
    private String dayOfWeek;

    @JsonProperty("day_of_year")
    private String dayOfYear;

    private boolean dst;

    @JsonProperty("dst_from")
    private String dstFrom;

    @JsonProperty("dst_offset")
    private Long dstOffset;

    @JsonProperty("dst_until")
    private String dstUntil;

    @JsonProperty("raw_offset")
    private int rawOffset;

    @JsonProperty("timezone")
    private String timeZone;

    @JsonProperty("unixtime")
    private String unixTime;

    @JsonProperty("utc_datetime")
    private String utcDateTime;

    @JsonProperty("utc_offset")
    private String utsOffset;

    @JsonProperty("week_number")
    private int weekNumber;

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Timezone{" +
                "abbreviation='" + abbreviation + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", dayOfYear='" + dayOfYear + '\'' +
                ", dst=" + dst +
                ", dstFrom='" + dstFrom + '\'' +
                ", dstOffset=" + dstOffset +
                ", dstUntil='" + dstUntil + '\'' +
                ", rawOffset=" + rawOffset +
                ", timeZone='" + timeZone + '\'' +
                ", unixTime='" + unixTime + '\'' +
                ", utcDateTime='" + utcDateTime + '\'' +
                ", utsOffset='" + utsOffset + '\'' +
                ", weekNumber=" + weekNumber +
                '}';
    }
}