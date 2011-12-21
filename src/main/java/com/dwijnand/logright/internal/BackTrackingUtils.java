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

        StackTraceElement[] callerData = le.getCallerData();
        if (callerData != null && callerData.length > 0) {
            BackTrackingMatchBean matchBean =
                doFindMatch(le, convertionTarget, ca);
            if (matchBean == null) {
                logFailedMatch(le, ca, convertionTarget);
            } else
                return matchBean;
        }
        return null;
    }

    static BackTrackingMatchBean doFindMatch(ILoggingEvent le,
        String convertionTarget, ContextAware ca) {

        String loggerName = le.getLoggerName();
        StackTraceElement[] stackTrace = le.getCallerData();
        for (StackTraceElement ste : stackTrace) {
            String className = ste.getClassName();
            if (loggerName.equals(className))
                return new BackTrackingMatchBean(ste);
            else {
                try {
                    Class<?> loggingClass = Class.forName(loggerName);
                    Class<?> stackTraceElementClass = Class.forName(className);
                    if (stackTraceElementClass.isAssignableFrom(loggingClass))
                        return new BackTrackingMatchBean(ste, className);
                } catch (ClassNotFoundException e) {
                    logFailedMatch(le, ca, convertionTarget,
                        "Failed to find class: " + loggerName);
                }
            }
        }
        return null;
    }

    private static void logFailedMatch(ILoggingEvent le, ContextAware ca,
        String convertionTarget) {

        String warnMessage =
            format("Failed to find {}. (Logger name: {}, caller data to "
                + "follow)", convertionTarget, le.getLoggerName());
        doLogFailedMatch(le, ca, warnMessage);
    }

    private static void logFailedMatch(ILoggingEvent le, ContextAware ca,
        String convertionTarget, String reason) {

        String warnMessage =
            format("Failed to find {}. (Logger name: {}, reason: {}, "
                + "caller data to follow)", convertionTarget,
                le.getLoggerName(), reason);
        doLogFailedMatch(le, ca, warnMessage);
    }

    private static void doLogFailedMatch(ILoggingEvent le, ContextAware ca,
        String warnMessage) {

        ca.addWarn(warnMessage);
        StackTraceElement[] callerData = le.getCallerData();
        for (int i = 0; i < callerData.length; i++) {
            StackTraceElement e = callerData[i];
            ca.addInfo(format(" callerData[{}]: {}", i, e));
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
