package com.ef.parser;

import com.ef.business.FileBusiness;
import com.ef.exception.InvalidFileException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest({"accesslog=/test.log"})
public class FileBusinessFailTests {

    @Autowired
    private FileBusiness fileBusiness;

    @Test(expected = InvalidFileException.class)
    public void testReadAndProcessFile() {
        fileBusiness.readAndProcessFile();
    }
}
