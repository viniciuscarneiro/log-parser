package com.ef.business;

import com.ef.exception.InvalidFileException;
import com.ef.model.AccessLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileBusiness {

    private static final String SPLIT_DELIMITER = "|";
    private static final String DATE_TIME_LOG_FILE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationBusiness applicationBusiness;
    @Autowired
    private AccessLogBusiness accessLogBusiness;
    @Autowired
    private BlockedIpBusiness blockedIpBusiness;

    @Value("${accesslog:}")
    private String accessLog;


    public void readAndProcessFile() {
        if (StringUtils.isNotBlank(accessLog)) {
            try (Stream<String> stream = Files.lines(Paths.get(accessLog))) {
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


    private void parseLine(String line) {
        StringTokenizer st = new StringTokenizer(line, SPLIT_DELIMITER);
        while (st.hasMoreTokens()) {
            this.accessLogBusiness.save(
                    new AccessLog.Builder()
                            .withDate(LocalDateTime.parse(st.nextToken(), DateTimeFormatter.ofPattern(DATE_TIME_LOG_FILE_PATTERN)))
                            .withIp(st.nextToken())
                            .withRequestMethod(st.nextToken())
                            .withHttpResponseStatus(st.nextToken())
                            .withUserAgent(st.nextToken()).build());
        }

    }

    private void process(Stream<String> stream) {
        this.applicationBusiness.wipeDatabase();
        for (String line : stream.collect(Collectors.toList())) {
            this.parseLine(line);
        }
    }

}
