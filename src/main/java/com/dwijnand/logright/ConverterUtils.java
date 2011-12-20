package com.dwijnand.logright;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

final class ConverterUtils {
    private ConverterUtils() {
        // Utility class
    }

    static StackTraceElement getStackTraceElementForLogger(ILoggingEvent le,
        String convertionTarget, ContextAware ca) {
        String loggerName = le.getLoggerName();
        StackTraceElement[] callerData = le.getCallerData();
        if (callerData != null && callerData.length > 0) {
            StackTraceElement ste =
                getStackTraceElementForLogger(callerData, loggerName);
            if (ste == null) {
                FormattingTuple ft =
                    MessageFormatter.format("Failed to find {}. "
                        + "(Logger name: {}, caller data to follow)",
                        convertionTarget, loggerName);
                ca.addWarn(ft.getMessage());
                for (int i = 0; i < callerData.length; i++) {
                    StackTraceElement e = callerData[i];
                    ft =
                        MessageFormatter.format("CallerData[{}] classname: {}",
                            i, e.getClassName());
                    ca.addInfo(ft.getMessage());
                }
            } else
                return ste;
        }
        return null;
    }

    static StackTraceElement getStackTraceElementForLogger(
        StackTraceElement[] stackTrace, String loggerName) {

        if (stackTrace != null) {
            for (StackTraceElement ste : stackTrace) {
                String className = ste.getClassName();
                if (loggerName.startsWith(className)
                    || loggerName.endsWith(className + ")"))
                    return ste;
            }
        }
        return null;
    }
}