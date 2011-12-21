package com.dwijnand.logright.testsupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractClass {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void abstractClassLogs() {
        LogMeSomething.log(logger, "The abstract class logs");
    }
}
