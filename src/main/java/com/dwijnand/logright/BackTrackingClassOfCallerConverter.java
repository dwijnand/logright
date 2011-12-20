package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

// TODO: Figure out if there is a way to support the Abbreviator better
public class BackTrackingClassOfCallerConverter extends ClassOfCallerConverter {
    @Override
    protected String getFullyQualifiedName(ILoggingEvent le) {
        StackTraceElement targetStackTraceElement =
            ConverterUtils.getStackTraceElementForLogger(le, "class of caller",
                this);
        if (targetStackTraceElement == null)
            return super.getFullyQualifiedName(le);
        return targetStackTraceElement.getClassName();
    }
}
