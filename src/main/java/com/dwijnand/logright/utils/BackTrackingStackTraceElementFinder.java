package com.dwijnand.logright.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultNotFoundBuilder;

public class BackTrackingStackTraceElementFinder implements
    StackTraceElementFinder {

    public static final BackTrackingStackTraceElementFinder INSTANCE =
        new BackTrackingStackTraceElementFinder();

    private BackTrackingStackTraceElementFinder() {
        // Singleton class
    }

    @Override
    public Result find(ILoggingEvent le) {
        String loggerName = le.getLoggerName();
        StackTraceElement[] callerData = le.getCallerData();
        if (callerData != null)
            return find(loggerName, callerData);
        ResultNotFoundBuilder notFoundBuilder =
            Result.notFoundBuilder(loggerName);
        return notFoundBuilder.addCause("No caller data found").build();
    }

    private Result find(String loggerName, StackTraceElement[] callerData) {

        for (StackTraceElement ste : callerData) {
            String className = ste.getClassName();
            if (loggerName.equals(className))
                return Result.found(ste);
            else {
                try {
                    Class<?> loggingClass = Class.forName(loggerName);
                    Class<?> stackTraceElementClass = Class.forName(className);
                    if (stackTraceElementClass.isAssignableFrom(loggingClass))
                        return Result.found(ste, className);
                } catch (ClassNotFoundException e) {
                    return Result.notFoundBuilder(loggerName)
                        .addCause("Failed to find class: " + loggerName)
                        .addCallerData(callerData).build();
                }
            }
        }
        return Result.notFoundBuilder(loggerName).addCallerData(callerData)
            .build();
    }
}
