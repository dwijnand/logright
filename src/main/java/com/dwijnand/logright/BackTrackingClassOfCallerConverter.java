package com.dwijnand.logright;

import com.dwijnand.logright.utils.DefaultStackTraceElementFinder;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinderResult;
import com.dwijnand.logright.utils.StackTraceElementFinderResult.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinderResult.ResultNotFound;

public class BackTrackingClassOfCallerConverter extends ClassOfCallerConverter {
    private final StackTraceElementFinder stackTraceElementFinder;

    public BackTrackingClassOfCallerConverter() {
        this(new DefaultStackTraceElementFinder());
    }

    public BackTrackingClassOfCallerConverter(
        StackTraceElementFinder stackTraceElementFinder) {
        this.stackTraceElementFinder = stackTraceElementFinder;
    }

    @Override
    protected String getFullyQualifiedName(ILoggingEvent le) {
        StackTraceElementFinderResult result = stackTraceElementFinder.find(le);

        if (result.found())
            return ((ResultFound) result).getClassOfCaller();

        ((ResultNotFound) result).addCause(this);
        return super.getFullyQualifiedName(le);
    }
}
