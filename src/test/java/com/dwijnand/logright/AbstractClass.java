package com.dwijnand.logright;

import org.slf4j.Logger;

public abstract class AbstractClass {
    protected final Logger logger = InstanceLoggerFactory.getLogger(getClass(),
        AbstractClass.class);

    public void abstractClassLogs() {
        LogMeSomething.log(logger, "The abstract class logs");
    }
}
