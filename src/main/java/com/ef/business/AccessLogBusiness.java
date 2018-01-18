package com.ef.business;

import com.ef.model.AccessLog;
import com.ef.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccessLogBusiness {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public void save(AccessLog accessLog) {
        this.accessLogRepository.save(accessLog);
    }

    public Optional<List<String>> executeSearch(DurationEnum duration, LocalDateTime date, Integer threshold) {
        return this.accessLogRepository.findByDateRangeAndCount(date,
                date.plus(duration.getDuration()), threshold);
    }

    public void truncateAccessLogTable() {
        this.accessLogRepository.deleteAll();
    }
}
