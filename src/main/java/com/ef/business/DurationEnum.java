package com.ef.business;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public enum DurationEnum {

    DAILY("daily", Duration.ofDays(1)),
    HOURLY("hourly", Duration.ofHours(1));

    private final String description;
    private final Duration duration;

    DurationEnum(String description, Duration duration) {
        this.description = description;
        this.duration = duration;
    }

    public static DurationEnum of(String description) {
        return Arrays.stream(DurationEnum.values())
                .filter(e -> e.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", description)));
    }

    public Duration getDuration() {
        return duration;
    }

}