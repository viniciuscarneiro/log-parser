package com.ef.business;

import com.ef.model.DurationEnum;
import com.ef.model.AccessLog;
import com.ef.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AccessLogBusiness {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public void save(AccessLog accessLog) {
        this.accessLogRepository.save(accessLog);
    }

    public List<String> executeSearch(DurationEnum duration, LocalDateTime date, Integer threshold) {
        Map<String, LocalDateTime> dateRange = duration.getDateRange(date);
        return this.accessLogRepository.findByDateRangeAndCount(dateRange.get(DurationEnum.START_DATE),
                dateRange.get(DurationEnum.END_DATE),
                threshold);
    }

    public void truncateAccessLogTable() {
        this.accessLogRepository.deleteAll();
    }
}
