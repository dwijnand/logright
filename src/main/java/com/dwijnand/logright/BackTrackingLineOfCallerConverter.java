package com.dwijnand.logright;

import com.dwijnand.logright.utils.BackTrackingStackTraceElementFinder;

public class BackTrackingLineOfCallerConverter extends
    LineOfCallerConverterBase {

    public BackTrackingLineOfCallerConverter() {
        super(BackTrackingStackTraceElementFinder.INSTANCE);
    }
}
