package com.dwijnand.logright.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;

public interface StackTraceElementFinder {
    StackTraceElementFinderResult find(ILoggingEvent le);
}
