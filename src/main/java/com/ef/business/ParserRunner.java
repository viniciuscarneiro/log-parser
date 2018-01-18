package com.ef.business;

import com.ef.enums.Search;
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

import javax.annotation.Resource;
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
    private static final String DATE_TIME_LOG_PATTERN2 = "yyyy-MM-dd.HH:mm:ss";
    private static final String BLOCK_MESSAGE = "BLOCKED - This IP made more than %s or more requests ";

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${accesslog:}")
    private String accesslogParameter;
    @Value("${startDate:}")
    private String startDateParameter;
    @Value("${duration:}")
    private String durationParameter;
    @Value("${threshold:}")
    private String thresholdParameter;

    @Autowired
    private AccessLogBusiness accessLogBusiness;
    @Autowired
    private BlockedIpBusiness blockedIpBusiness;

    @Resource(name = "${duration:hourly}")
    private Search search;

    public void run() {
        this.validateInputParameters();
        this.readFile();
        Integer threshold = Integer.valueOf(thresholdParameter);
        this.parseReturnedIps(this.search.executeSearch(LocalDateTime.parse(startDateParameter, DateTimeFormatter.ofPattern(DATE_TIME_LOG_PATTERN2)), threshold), threshold);
    }

    private void parseReturnedIps(List<String> ipsList, Integer threshold) {
        ipsList.forEach(ip -> {
            log.info(ip);
            blockedIpBusiness.save(new BlockedIp().setIp(ip).setComment(String.format(BLOCK_MESSAGE, threshold)));
        });
    }

    private void validateInputParameters() {
        if (StringUtils.isBlank(startDateParameter)) {
            throw new InvalidParameterException("startDate parameter is required!");
        } else if (StringUtils.isBlank(durationParameter)) {
            throw new InvalidParameterException("duration  parameter is required!");
        } else if (StringUtils.isBlank(thresholdParameter)) {
            throw new InvalidParameterException("threshold  parameter is required!");
        }
    }

    private void readFile() {
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
        this.cleanDatabase();
        for (String line : stream.collect(Collectors.toList())) {
            this.parseLine(line);
        }
    }

    private void cleanDatabase() {
        this.accessLogBusiness.truncateAccessLogTable();
        this.blockedIpBusiness.truncateBlockedIpTable();
    }

    private void parseLine(String line) {
        StringTokenizer st = new StringTokenizer(line, SPLIT_DELIMITER);
        while (st.hasMoreTokens()) {
            this.accessLogBusiness.save(new AccessLog()
                    .setDate(LocalDateTime.parse(st.nextToken(), DateTimeFormatter.ofPattern(DATE_TIME_LOG_PATTERN)))
                    .setIp(st.nextToken())
                    .setRequestMethod(st.nextToken())
                    .setHttpResponseStatus(st.nextToken())
                    .setUserAgent(st.nextToken()));
        }

    }


}
