package com.dwijnand.logright;

import com.dwijnand.logright.utils.BackTrackingStackTraceElementFinder;

public class BackTrackingMethodOfCallerConverter extends
    MethodOfCallerConverterBase {

    public BackTrackingMethodOfCallerConverter() {
        super(BackTrackingStackTraceElementFinder.INSTANCE);
    }
}
