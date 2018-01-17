package com.ef.enums;

import com.google.common.collect.ImmutableMap;

import java.time.LocalDateTime;
import java.util.Map;

public enum DurationEnum {

    DAILY("daily", 200, DailySearch.class), HOURLY("hourly", 500, HourlySearch.class);

    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";

    private String description;
    private Integer maxRequestLimit;
    private Class implementation;

    DurationEnum(String description, Integer maxRequestLimit, Class implementation) {
        this.description = description;
        this.maxRequestLimit = maxRequestLimit;
        this.implementation = implementation;
    }

    public Map<String, LocalDateTime> getDateRange(LocalDateTime date) {
        switch (this) {
            case HOURLY:
                return getBuild(date);
            case DAILY:
                return getBuild(date);
            default:
                throw new AssertionError("Unknown duration " + this);
        }
    }

    private ImmutableMap<String, LocalDateTime> getBuild(LocalDateTime date) {
        return ImmutableMap.<String, LocalDateTime>builder()
                .put(START_DATE, LocalDateTime.now())
                .put(END_DATE, LocalDateTime.now())
                .build();
    }


}
