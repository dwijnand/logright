package com.dwijnand.logright;

import com.dwijnand.logright.utils.DefaultStackTraceElementFinder;

import ch.qos.logback.classic.pattern.FileOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinderResult;
import com.dwijnand.logright.utils.StackTraceElementFinderResult.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinderResult.ResultNotFound;

public class BackTrackingFileOfCallerConverter extends FileOfCallerConverter {
    private final StackTraceElementFinder stackTraceElementFinder;

    public BackTrackingFileOfCallerConverter() {
        this(new DefaultStackTraceElementFinder());
    }

    public BackTrackingFileOfCallerConverter(
        StackTraceElementFinder stackTraceElementFinder) {
        this.stackTraceElementFinder = stackTraceElementFinder;
    }

    @Override
    public String convert(ILoggingEvent le) {
        StackTraceElementFinderResult result = stackTraceElementFinder.find(le);

        if (result.found())
            return ((ResultFound) result).getStackTraceElement().getFileName();

        ((ResultNotFound) result).addCause(this);
        return super.convert(le);
    }
}
