package com.dwijnand.logright.testsupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleStatelessClass {
    private static final Logger logger = LoggerFactory
        .getLogger(SimpleStatelessClass.class);

    public static void simpleStatelessClassLogs() {
        LogMeSomething.log(logger, "Hello");
    }
}
