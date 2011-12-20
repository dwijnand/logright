package com.dwijnand.logright.internal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import org.slf4j.helpers.MessageFormatter;

public final class BackTrackingUtils {
    private BackTrackingUtils() {
        // Utility class
    }

    public static BackTrackingMatchBean findMatch(ILoggingEvent le,
        String convertionTarget, ContextAware ca) {

        String loggerName = le.getLoggerName();
        StackTraceElement[] callerData = le.getCallerData();
        if (callerData != null && callerData.length > 0) {
            BackTrackingMatchBean matchBean = findMatch(callerData, loggerName);
            if (matchBean == null) {
                logFailedMatch(le, convertionTarget, ca);
            } else
                return matchBean;
        }
        return null;
    }

    static BackTrackingMatchBean findMatch(StackTraceElement[] stackTrace,
        String loggerName) {

        if (stackTrace != null) {
            for (StackTraceElement ste : stackTrace) {
                String className = ste.getClassName();
                // TODO: Test if its more better to also test equals first
                if (loggerName.startsWith(className))
                    return new BackTrackingMatchBean(ste);
                else if (loggerName.endsWith(className + ")"))
                    return new BackTrackingMatchBean(ste, loggerName);
            }
        }
        return null;
    }

    private static void logFailedMatch(ILoggingEvent le,
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

    public static class BackTrackingMatchBean {
        public final StackTraceElement ste;
        public String classOfCaller;

        public BackTrackingMatchBean(StackTraceElement ste) {
            this.ste = ste;
            classOfCaller = ste.getClassName();
        }

        BackTrackingMatchBean(StackTraceElement ste, String classOfCaller) {
            this.ste = ste;
            this.classOfCaller = classOfCaller;
        }
    }
}
