package com.dwijnand.logright;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractClass {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void abstractClassLogs() {
        logger.info("The abstract class logs");
    }
}
