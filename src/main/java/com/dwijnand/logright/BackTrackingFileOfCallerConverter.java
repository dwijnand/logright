package com.dwijnand.logright;

import com.dwijnand.logright.utils.BackTrackingStackTraceElementFinder;

public class BackTrackingFileOfCallerConverter extends
    FileOfCallerConverterBase {

    public BackTrackingFileOfCallerConverter() {
        super(BackTrackingStackTraceElementFinder.INSTANCE);
    }
}
