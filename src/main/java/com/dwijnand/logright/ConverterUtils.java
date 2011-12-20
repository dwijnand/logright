package com.dwijnand.logright;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import org.slf4j.helpers.MessageFormatter;

final class ConverterUtils {
    private ConverterUtils() {
        // Utility class
    }

    static StackTraceElement getStackTraceElementForLogger(ILoggingEvent le,
        String convertionTarget, ContextAware ca) {
        return matchStackTraceElementWithLogger(le, convertionTarget, ca)
            .getStackTraceElement();
    }

    static StackTraceElementMatch matchStackTraceElementWithLogger(
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
                    return new StackTraceElementExactMatch(ste);
                else if (loggerName.endsWith(className + ")"))
                    return new StackTraceElementSubclassMatch(ste, loggerName);
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

    static interface StackTraceElementMatch {
        StackTraceElement getStackTraceElement();

        String getClassOfCaller();
    }

    private static abstract class AbstractStackTraceElementMatch implements
        StackTraceElementMatch {

        private final StackTraceElement stackTraceElement;

        public AbstractStackTraceElementMatch(
            StackTraceElement stackTraceElement) {
            this.stackTraceElement = stackTraceElement;
        }

        public StackTraceElement getStackTraceElement() {
            return stackTraceElement;
        }
    }

    private static final class StackTraceElementExactMatch extends
        AbstractStackTraceElementMatch {

        public StackTraceElementExactMatch(StackTraceElement stackTraceElement) {
            super(stackTraceElement);
        }

        public String getClassOfCaller() {
            return getStackTraceElement().getClassName();
        }
    }

    private static final class StackTraceElementSubclassMatch extends
        AbstractStackTraceElementMatch {

        private final String classOfCaller;

        public StackTraceElementSubclassMatch(
            StackTraceElement stackTraceElement, String classOfCaller) {
            super(stackTraceElement);
            this.classOfCaller = classOfCaller;
        }

        public String getClassOfCaller() {
            return classOfCaller;
        }
    }
}
