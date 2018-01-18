package com.ef.runner;

import com.ef.business.FileBusiness;
import com.ef.business.SearchBusiness;
import com.ef.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParserRunner {

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
        this.fileBusiness.readAndProcessFile();
        this.searchBusiness.executeAndProcessSearch();
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
