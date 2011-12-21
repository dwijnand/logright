package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.MethodOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.DefaultStackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultNotFound;

public class BackTrackingMethodOfCallerConverter extends
    MethodOfCallerConverter {

    private final StackTraceElementFinder stackTraceElementFinder;

    public BackTrackingMethodOfCallerConverter() {
        this(new DefaultStackTraceElementFinder());
    }

    public BackTrackingMethodOfCallerConverter(
        StackTraceElementFinder stackTraceElementFinder) {
        this.stackTraceElementFinder = stackTraceElementFinder;
    }

    @Override
    public String convert(ILoggingEvent le) {
        Result result = stackTraceElementFinder.find(le);

        if (result.found())
            return ((ResultFound) result).getStackTraceElement()
                .getMethodName();

        ((ResultNotFound) result).addCause(this);
        return super.convert(le);
    }
}
