package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultNotFound;

public class MethodOfCallerConverterBase extends ClassicConverter {
    protected final StackTraceElementFinder stackTraceElementFinder;

    public MethodOfCallerConverterBase(
        StackTraceElementFinder stackTraceElementFinder) {
        this.stackTraceElementFinder = stackTraceElementFinder;
    }

    @Override
    public String convert(ILoggingEvent le) {
        Result result = stackTraceElementFinder.find(le);

        if (result.found())
            return ((ResultFound) result).getStackTraceElement()
                .getMethodName();

        ((ResultNotFound) result).logMessagesToContext(this);
        return getFallbackMethodName(le);
    }

    protected String getFallbackMethodName(ILoggingEvent le) {
        return CallerData.NA;
    }

}
