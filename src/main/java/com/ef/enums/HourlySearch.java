package com.ef.enums;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("hourly")
public class HourlySearch implements Search {

    @Override
    public List<String> executeSearch(LocalDateTime date, Integer threshold) {
        DurationEnum.DAILY.getDateRange(date);
        return null;
    }
}
