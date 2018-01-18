package com.ef.business;

import com.google.common.collect.ImmutableMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public enum DurationEnum implements Duration {

    DAILY("daily") {
        @Override
        public Map<String, LocalDateTime> getDateRange(LocalDateTime date) {
            return ImmutableMap.<String, LocalDateTime>builder()
                    .put(START_DATE, date)
                    .put(END_DATE, date.plusDays(1))
                    .build();
        }
    }, HOURLY("hourly") {
        @Override
        public Map<String, LocalDateTime> getDateRange(LocalDateTime date) {
            return ImmutableMap.<String, LocalDateTime>builder()
                    .put(START_DATE, date)
                    .put(END_DATE, date.plusHours(1))
                    .build();
        }
    };

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    private final String description;

    DurationEnum(String description) {
        this.description = description;
    }

    public static DurationEnum of(String description) {
        return Arrays.stream(DurationEnum.values())
                .filter(e -> e.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", description)));
    }
}