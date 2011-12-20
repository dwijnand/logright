package com.dwijnand.logright;

import com.dwijnand.logright.utils.LogMeSomething;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomeClass {
    private static final Logger logger = LoggerFactory
        .getLogger(SomeClass.class);

    public static void main(String[] args) {
        LogMeSomething.log(logger, "Hello");
    }
}
