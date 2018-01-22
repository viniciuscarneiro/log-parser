package com.ef.parser;

import com.ef.exception.InvalidParameterException;
import com.ef.runner.ParserRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserRunnerFailTests {

    @Autowired
    private ParserRunner parserRunner;

    @Test(expected = InvalidParameterException.class)
    public void testRun() {
        parserRunner.run();
    }
}
