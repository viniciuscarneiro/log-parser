package com.ef.business;

import com.ef.model.DurationEnum;
import com.ef.exception.InvalidFileException;
import com.ef.exception.InvalidParameterException;
import com.ef.model.AccessLog;
import com.ef.model.BlockedIp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ParserRunner {

    private static final String SPLIT_DELIMITER = "|";
    private static final String DATE_TIME_LOG_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATE_TIME_PARAMETER_PATTERN = "yyyy-MM-dd.HH:mm:ss";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${accesslog:}")
    private String accesslogParameter;
    @Value("${startDate:}")
    private String startDateParameter;
    @Value("${duration:}")
    private String durationParameter;
    @Value("${threshold:}")
    private Integer thresholdParameter;

    @Autowired
    private AccessLogBusiness accessLogBusiness;
    @Autowired
    private BlockedIpBusiness blockedIpBusiness;

    public void run() {
        this.validateInputParameters();
        this.readAndProcessFile();
        this.parseReturnedIps(
                this.accessLogBusiness.executeSearch(DurationEnum.of(durationParameter), LocalDateTime.parse(startDateParameter, DateTimeFormatter.ofPattern(DATE_TIME_PARAMETER_PATTERN)), thresholdParameter));
    }

    private void parseReturnedIps(List<String> ipsList) {
        ipsList.forEach(ip -> {
            log.info(ip);
            blockedIpBusiness.save(new BlockedIp.Builder().withIp(ip).withThreshold(thresholdParameter).build());
        });
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

    private void readAndProcessFile() {
        if (StringUtils.isNotBlank(accesslogParameter)) {
            try (Stream<String> stream = Files.lines(Paths.get(accesslogParameter))) {
                this.process(stream);
            } catch (NoSuchFileException e) {
                log.error("File not found", e);
                throw new InvalidFileException(e);
            } catch (UncheckedIOException | IOException e) {
                log.error("Maybe something is wrong with your input log file", e);
                throw new InvalidFileException(e);
            }
        }
    }

    private void process(Stream<String> stream) {
        this.wipeDatabase();
        for (String line : stream.collect(Collectors.toList())) {
            this.parseLine(line);
        }
    }

    private void wipeDatabase() {
        this.accessLogBusiness.truncateAccessLogTable();
        this.blockedIpBusiness.truncateBlockedIpTable();
    }

    private void parseLine(String line) {
        StringTokenizer st = new StringTokenizer(line, SPLIT_DELIMITER);
        while (st.hasMoreTokens()) {
            this.accessLogBusiness.save(
                    new AccessLog.Builder()
                            .withDate(LocalDateTime.parse(st.nextToken(), DateTimeFormatter.ofPattern(DATE_TIME_LOG_PATTERN)))
                            .withIp(st.nextToken())
                            .withRequestMethod(st.nextToken())
                            .withHttpResponseStatus(st.nextToken())
                            .withUserAgent(st.nextToken()).build());
        }

    }


}
