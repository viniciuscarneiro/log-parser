package com.ef.enums;

import com.google.common.collect.ImmutableMap;

import java.time.LocalDateTime;
import java.util.Map;

public enum DurationEnum {

    DAILY("daily"), HOURLY("hourly");

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    private String description;


    DurationEnum(String description) {
        this.description = description;
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
