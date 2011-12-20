package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.internal.ConverterUtils;
import com.dwijnand.logright.internal.ConverterUtils.StackTraceElementMatch;

// TODO: Figure out if there is a way to support the Abbreviator better
public class BackTrackingClassOfCallerConverter extends ClassOfCallerConverter {
    @Override
    protected String getFullyQualifiedName(ILoggingEvent le) {
        StackTraceElementMatch stackTraceElementMatch =
            ConverterUtils.matchStackTraceElementWithLogger(le,
                "class of caller", this);

        if (stackTraceElementMatch == null)
            return super.getFullyQualifiedName(le);

        return stackTraceElementMatch.classOfCaller;
    }
}
