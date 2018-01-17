package com.ef.business;

import com.ef.exception.InvalidFileException;
import com.ef.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.stream.Stream;

@Component
public class ParserRunner {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${accesslog:}")
    private String accesslogParameter;
    @Value("${startDate:}")
    private String startDateParameter;
    @Value("${duration:}")
    private String durationParameter;
    @Value("${threshold:}")
    private String thresholdParameter;

    public void run() {
        this.validateInputParameters();
        this.readFile();
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
                stream.forEach(line -> parseLine(line));
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
        StringTokenizer tokenizer = new StringTokenizer(line, "(.*)\\|(.*)\\|(.*)\\|(.*)\\|(.*)");

        while (tokenizer.hasMoreTokens()) {

            while (tokenizer.hasMoreElements()) {
                log.info("Date " + tokenizer.nextElement());
            }
            tokenizer.nextToken("|");
        }
    }
}
