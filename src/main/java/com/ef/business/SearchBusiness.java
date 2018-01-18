package com.ef.business;

import com.ef.model.DurationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SearchBusiness {

    private static final String DATE_TIME_PARAMETER_PATTERN = "yyyy-MM-dd.HH:mm:ss";

    @Value("${startDate:}")
    private String startDate;
    @Value("${duration:}")
    private String duration;
    @Value("${threshold:}")
    private Integer threshold;

    @Autowired
    private ApplicationBusiness applicationBusiness;
    @Autowired
    private AccessLogBusiness accessLogBusiness;
    @Autowired
    private BlockedIpBusiness blockedIpBusiness;

    public void executeAndProcessSearch() {
        this.accessLogBusiness
                .executeSearch(DurationEnum.of(duration), LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern(DATE_TIME_PARAMETER_PATTERN)), threshold)
                .ifPresent(result -> processSearchResult(threshold, result));
    }

    private void processSearchResult(Integer threshold, List<String> ipsList) {
        this.blockedIpBusiness.processReturnedIps(threshold, ipsList);
    }
}
