package com.dwijnand.logright;

import com.dwijnand.logright.utils.BackTrackingStackTraceElementFinder;

public class BackTrackingClassOfCallerConverter extends
    ClassOfCallerConverterBase {

    public BackTrackingClassOfCallerConverter() {
        super(BackTrackingStackTraceElementFinder.INSTANCE);
    }
}
