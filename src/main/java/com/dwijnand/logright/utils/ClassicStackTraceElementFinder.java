package com.dwijnand.logright.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * This implements Logback's default way of finding the StackTraceElement: the
 * first one of the caller data.
 */
public class ClassicStackTraceElementFinder implements StackTraceElementFinder {
    @Override
    public StackTraceElementFinderResult find(ILoggingEvent le) {
        StackTraceElement[] callerData = le.getCallerData();

        if (callerData != null && callerData.length > 0)
            return StackTraceElementFinderResult.found(callerData[0]);

        return StackTraceElementFinderResult
            .notFoundBuilder(le.getLoggerName()).addCallerData(callerData)
            .addCause("No caller data exists").build();
    }
}
