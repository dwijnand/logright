package com.dwijnand.logright.logging;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class BackTrackingLineOfCallerConverter extends LineOfCallerConverter {
    @Override
    public String convert(ILoggingEvent le) {
        StackTraceElement targetStackTraceElement =
            BackTrackingConverterUtils.getOrLogTargetStackTraceElement(le,
                "line of caller", this);
        if (targetStackTraceElement == null)
            return super.convert(le);
        return Integer.toString(targetStackTraceElement.getLineNumber());
    }
}
