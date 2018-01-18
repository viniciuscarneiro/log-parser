package com.ef;

import com.ef.runner.ParserRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Parser {
    public static void main(String[] args) {
        SpringApplication
                .run(Parser.class, args)
                .getBean(ParserRunner.class)
                .run();
    }
}
