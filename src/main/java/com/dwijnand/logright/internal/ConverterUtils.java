package com.dwijnand.logright.internal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import org.slf4j.helpers.MessageFormatter;

public final class ConverterUtils {
    private ConverterUtils() {
        // Utility class
    }

    public static StackTraceElement getStackTraceElementForLogger(
        ILoggingEvent le, String convertionTarget, ContextAware ca) {

        return matchStackTraceElementWithLogger(le, convertionTarget, ca).ste;
    }

    public static StackTraceElementMatch matchStackTraceElementWithLogger(
        ILoggingEvent le, String convertionTarget, ContextAware ca) {

        String loggerName = le.getLoggerName();
        StackTraceElement[] callerData = le.getCallerData();
        if (callerData != null && callerData.length > 0) {
            StackTraceElementMatch stem =
                matchStackTraceElementWithLogger(callerData, loggerName);
            if (stem == null) {
                logFailedCallerDataMatch(le, convertionTarget, ca);
            } else
                return stem;
        }
        return null;
    }

    static StackTraceElementMatch matchStackTraceElementWithLogger(
        StackTraceElement[] stackTrace, String loggerName) {

        if (stackTrace != null) {
            for (StackTraceElement ste : stackTrace) {
                String className = ste.getClassName();
                // TODO: Consider using equals... renaming match class..
                if (loggerName.startsWith(className))
                    return new StackTraceElementMatch(ste);
                else if (loggerName.endsWith(className + ")"))
                    return new StackTraceElementMatch(ste, loggerName);
            }
        }
        return null;
    }

    private static void logFailedCallerDataMatch(ILoggingEvent le,
        String convertionTarget, ContextAware ca) {

        ca.addWarn(format("Failed to find {}. (Logger name: {}, "
            + "caller data to follow)", convertionTarget, le.getLoggerName()));
        StackTraceElement[] callerData = le.getCallerData();
        for (int i = 0; i < callerData.length; i++) {
            StackTraceElement e = callerData[i];
            ca.addInfo(format(" callerData[{}] classname: {}", i,
                e.getClassName()));
        }
    }

    private static String format(String messagePattern, Object... args) {
        return MessageFormatter.arrayFormat(messagePattern, args).getMessage();
    }

    public static class StackTraceElementMatch {
        public final StackTraceElement ste;
        public String classOfCaller;

        public StackTraceElementMatch(StackTraceElement ste) {
            this.ste = ste;
            classOfCaller = ste.getClassName();
        }

        StackTraceElementMatch(StackTraceElement ste, String classOfCaller) {
            this.ste = ste;
            this.classOfCaller = classOfCaller;
        }
    }
}