package com.dwijnand.logright.testsupport;

import org.slf4j.Logger;

public class LogMeSomething {
    public static void log(Logger logger, String message) {
        logger.info(message);
    }
}
