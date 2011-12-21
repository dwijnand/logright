package com.dwijnand.logright;

import com.dwijnand.logright.utils.ClassicStackTraceElementFinder;

public class LineOfCallerConverter extends LineOfCallerConverterBase {
    public LineOfCallerConverter() {
        super(ClassicStackTraceElementFinder.INSTANCE);
    }
}
