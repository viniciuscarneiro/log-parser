package com.ef.model;

import com.google.common.collect.ImmutableMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public enum DurationEnum {

    DAILY("daily"), HOURLY("hourly");

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    private String description;

    DurationEnum(String description) {
        this.description = description;
    }

    public static DurationEnum of(String description) {
        return Arrays.stream(DurationEnum.values())
                .filter(e -> e.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", description)));
    }

    public Map<String, LocalDateTime> getDateRange(LocalDateTime date) {
        switch (this) {
            case HOURLY:
                return buildRange(date, date.plusHours(1));
            case DAILY:
                return buildRange(date, date.plusDays(1));
            default:
                throw new IllegalStateException(String.format("Unsupported type %s.", this));
        }
    }

    private ImmutableMap<String, LocalDateTime> buildRange(LocalDateTime start, LocalDateTime end) {
        return ImmutableMap.<String, LocalDateTime>builder()
                .put(START_DATE, start)
                .put(END_DATE, end)
                .build();
    }
}
