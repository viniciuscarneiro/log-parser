package com.ef.enums;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("daily")
public class DailySearch implements Search {

    @Override
    public List<String> executeSearch(LocalDateTime date, Integer threshold) {
        return null;
    }
}
