package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.DefaultStackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultNotFound;

public class BackTrackingLineOfCallerConverter extends LineOfCallerConverter {
    private final StackTraceElementFinder stackTraceElementFinder;

    public BackTrackingLineOfCallerConverter() {
        this(new DefaultStackTraceElementFinder());
    }

    public BackTrackingLineOfCallerConverter(
        StackTraceElementFinder stackTraceElementFinder) {
        this.stackTraceElementFinder = stackTraceElementFinder;
    }

    @Override
    public String convert(ILoggingEvent le) {
        Result result = stackTraceElementFinder.find(le);

        if (result.found())
            return Integer.toString(((ResultFound) result)
                .getStackTraceElement().getLineNumber());

        ((ResultNotFound) result).addCause(this);
        return super.convert(le);
    }
}
