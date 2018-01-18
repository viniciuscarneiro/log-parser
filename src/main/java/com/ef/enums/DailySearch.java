package com.ef.enums;

import com.ef.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("daily")
public class DailySearch implements Search {

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Override
    public List<String> executeSearch(LocalDateTime date, Integer threshold) {
        Map<String, LocalDateTime> dateRange = DurationEnum.DAILY.getDateRange(date);
        return this.accessLogRepository.findByDateRangeAndCount(dateRange.get(DurationEnum.START_DATE),
                                                                dateRange.get(DurationEnum.END_DATE),
                                                                threshold);
    }
}
