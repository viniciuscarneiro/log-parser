package com.ef.parser;

import com.ef.business.FileBusiness;
import com.ef.business.SearchBusiness;
import com.ef.runner.ParserRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest({"accesslog=classpath:/test.log", "startDate=2017-01-01.13:00:00", "duration=hourly", "threshold=100"})
public class ParserRunnerTests {

    @MockBean
    private FileBusiness fileBusiness;
    @MockBean
    private SearchBusiness searchBusiness;

    @Autowired
    private ParserRunner parserRunner;

    @Test
    public void testRun() {
        parserRunner.run();
    }
}
