package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.internal.BackTrackingUtils;

public class BackTrackingLineOfCallerConverter extends LineOfCallerConverter {
    @Override
    public String convert(ILoggingEvent le) {
        StackTraceElement stackTraceElement =
            BackTrackingUtils.findMatch(le, "line of caller", this).ste;
        if (stackTraceElement == null)
            return super.convert(le);
        return Integer.toString(stackTraceElement.getLineNumber());
    }
}
