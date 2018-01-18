package com.ef.runner;

import com.ef.business.FileBusiness;
import com.ef.business.SearchBusiness;
import com.ef.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParserRunner {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${accesslog:}")
    private String accessLogParameter;
    @Value("${startDate:}")
    private String startDateParameter;
    @Value("${duration:}")
    private String durationParameter;
    @Value("${threshold:}")
    private Integer thresholdParameter;

    @Autowired
    private FileBusiness fileBusiness;
    @Autowired
    private SearchBusiness searchBusiness;

    public void run() {
        this.validateInputParameters();
        this.fileBusiness.readAndProcessFile(accessLogParameter, durationParameter, startDateParameter, thresholdParameter);
        this.searchBusiness.executeAndProcessSearch(durationParameter, startDateParameter, thresholdParameter);
    }

    private void validateInputParameters() {
        if (StringUtils.isBlank(startDateParameter)) {
            throw new InvalidParameterException("startDate");
        } else if (StringUtils.isBlank(durationParameter)) {
            throw new InvalidParameterException("duration");
        } else if (thresholdParameter == null) {
            throw new InvalidParameterException("threshold");
        }
    }


}
