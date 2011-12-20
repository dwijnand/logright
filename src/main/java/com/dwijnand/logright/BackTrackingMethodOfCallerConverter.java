package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.MethodOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class BackTrackingMethodOfCallerConverter extends
    MethodOfCallerConverter {

    @Override
    public String convert(ILoggingEvent le) {
        StackTraceElement targetStackTraceElement =
            BackTrackingConverterUtils.getStackTraceElementForLogger(le,
                "method of caller", this);
        if (targetStackTraceElement == null)
            return super.convert(le);
        return targetStackTraceElement.getMethodName();
    }
}
