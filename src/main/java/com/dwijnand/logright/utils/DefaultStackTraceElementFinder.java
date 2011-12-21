package com.dwijnand.logright.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.StackTraceElementFinderResult.ResultNotFoundBuilder;

public class DefaultStackTraceElementFinder implements StackTraceElementFinder {
    @Override
    public StackTraceElementFinderResult find(ILoggingEvent le) {
        String loggerName = le.getLoggerName();
        StackTraceElement[] callerData = le.getCallerData();
        if (callerData != null)
            return find(loggerName, callerData);
        ResultNotFoundBuilder notFoundBuilder =
            StackTraceElementFinderResult.notFoundBuilder(loggerName);
        return notFoundBuilder.addCause("No caller data found").build();
    }

    private StackTraceElementFinderResult find(String loggerName,
        StackTraceElement[] callerData) {

        for (StackTraceElement ste : callerData) {
            String className = ste.getClassName();
            if (loggerName.equals(className))
                return StackTraceElementFinderResult.found(ste);
            else {
                try {
                    Class<?> loggingClass = Class.forName(loggerName);
                    Class<?> stackTraceElementClass = Class.forName(className);
                    if (stackTraceElementClass.isAssignableFrom(loggingClass))
                        return StackTraceElementFinderResult.found(ste,
                            className);
                } catch (ClassNotFoundException e) {
                    return StackTraceElementFinderResult
                        .notFoundBuilder(loggerName)
                        .addCause("Failed to find class: " + loggerName)
                        .addCallerData(callerData).build();
                }
            }
        }
        return StackTraceElementFinderResult.notFoundBuilder(loggerName)
            .addCallerData(callerData).build();
    }
}
