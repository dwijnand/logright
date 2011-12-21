package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.NamedConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultNotFound;

public class ClassOfCallerConverterBase extends NamedConverter {
    protected final StackTraceElementFinder stackTraceElementFinder;

    public ClassOfCallerConverterBase(
        StackTraceElementFinder stackTraceElementFinder) {
        this.stackTraceElementFinder = stackTraceElementFinder;
    }

    @Override
    protected String getFullyQualifiedName(ILoggingEvent le) {
        Result result = stackTraceElementFinder.find(le);

        if (result.found())
            return ((ResultFound) result).getClassOfCaller();

        ((ResultNotFound) result).logMessagesToContext(this);
        return getFallbackFullyQualifiedName(le);
    }

    protected String getFallbackFullyQualifiedName(ILoggingEvent le) {
        return CallerData.NA;
    }
}
