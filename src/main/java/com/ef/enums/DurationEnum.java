package com.ef.enums;

import com.google.common.collect.ImmutableMap;

import java.time.LocalDateTime;
import java.util.Map;

public enum DurationEnum {

    DAILY("daily", 200), HOURLY("hourly", 500);

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    private String description;
    private Integer maxRequestLimit;

    DurationEnum(String description, Integer maxRequestLimit) {
        this.description = description;
        this.maxRequestLimit = maxRequestLimit;
    }

    public Map<String, LocalDateTime> getDateRange(LocalDateTime date) {
        switch (this) {
            case HOURLY:
                return buildRange(date, date.plusHours(1));
            case DAILY:
                return buildRange(date, date.plusDays(1));
            default:
                throw new AssertionError("Unknown duration " + this);
        }
    }

    private ImmutableMap<String, LocalDateTime> buildRange(LocalDateTime start, LocalDateTime end) {
        return ImmutableMap.<String, LocalDateTime>builder()
                .put(START_DATE, start)
                .put(END_DATE, end)
                .build();
    }


}
