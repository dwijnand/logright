package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.MethodOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.internal.BackTrackingUtils;

public class BackTrackingMethodOfCallerConverter extends
    MethodOfCallerConverter {

    @Override
    public String convert(ILoggingEvent le) {
        StackTraceElement stackTraceElement =
            BackTrackingUtils.findMatch(le, "method of caller", this).ste;
        if (stackTraceElement == null)
            return super.convert(le);
        return stackTraceElement.getMethodName();
    }
}
