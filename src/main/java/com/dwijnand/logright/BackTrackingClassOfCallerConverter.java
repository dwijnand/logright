package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class BackTrackingClassOfCallerConverter extends ClassOfCallerConverter {
    @Override
    protected String getFullyQualifiedName(ILoggingEvent le) {
        StackTraceElement targetStackTraceElement =
            BackTrackingConverterUtils.getStackTraceElementForLogger(le,
                "class of caller", this);
        if (targetStackTraceElement == null)
            return super.getFullyQualifiedName(le);
        return targetStackTraceElement.getClassName();
    }
}
