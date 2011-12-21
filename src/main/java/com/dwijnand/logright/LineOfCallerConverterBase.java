package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultNotFound;

public class LineOfCallerConverterBase extends ClassicConverter {
    protected final StackTraceElementFinder stackTraceElementFinder;

    public LineOfCallerConverterBase(
        StackTraceElementFinder stackTraceElementFinder) {
        this.stackTraceElementFinder = stackTraceElementFinder;
    }

    @Override
    public String convert(ILoggingEvent le) {
        Result result = stackTraceElementFinder.find(le);

        if (result.found())
            return Integer.toString(((ResultFound) result)
                .getStackTraceElement().getLineNumber());

        ((ResultNotFound) result).logMessagesToContext(this);
        return getFallbackLineNumber(le);
    }

    protected String getFallbackLineNumber(ILoggingEvent le) {
        return CallerData.NA;
    }

}
