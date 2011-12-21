package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.FileOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.utils.DefaultStackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultFound;
import com.dwijnand.logright.utils.StackTraceElementFinder.Result.ResultNotFound;

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
        Result result = stackTraceElementFinder.find(le);

        if (result.found())
            return ((ResultFound) result).getStackTraceElement().getFileName();

        ((ResultNotFound) result).logMessagesToContext(this);
        return super.convert(le);
    }
}
