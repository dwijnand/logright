package com.dwijnand.logright.utils;

import org.slf4j.Logger;

public class LogMeSomething {
    public static void log(Logger logger, String message) {
        logger.info("Yo: {}", message);
    }
}
