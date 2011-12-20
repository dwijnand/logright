package com.dwijnand.logright.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

final class BackTrackingConverterUtils {
    private BackTrackingConverterUtils() {
        // Utility class
    }

    static StackTraceElement getOrLogTargetStackTraceElement(ILoggingEvent le,
        String convertionTarget, ContextAware ca) {
        String loggerName = le.getLoggerName();
        String logCallerClassName = loggerName; // Assumption
        StackTraceElement[] callerData = le.getCallerData();
        if (callerData != null && callerData.length > 0) {
            StackTraceElement ste =
                getStackTraceElementWithClassName(callerData,
                    logCallerClassName);
            if (ste == null) {
                FormattingTuple ft =
                    MessageFormatter.format("Failed to find {}, "
                        + "falling back to Logback's so-so solution. "
                        + "Logger name: {} (caller data to follow)",
                        convertionTarget, loggerName);
                ca.addWarn(ft.getMessage());
                for (int i = 0; i < callerData.length; i++) {
                    StackTraceElement e = callerData[i];
                    ft =
                        MessageFormatter.format("ClassName {}: {}", i,
                            e.getClassName());
                    ca.addInfo(ft.getMessage());
                }
            } else
                return ste;
        }
        return null;
    }

    static StackTraceElement getStackTraceElementWithClassName(
        StackTraceElement[] stackTrace, String targetClassName) {

        if (stackTrace != null) {
            for (StackTraceElement ste : stackTrace) {
                if (ste.getClassName().startsWith(targetClassName))
                    return ste;
            }
        }
        return null;
    }
}
