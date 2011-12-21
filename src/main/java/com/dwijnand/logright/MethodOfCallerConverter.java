package com.dwijnand.logright;

import com.dwijnand.logright.utils.ClassicStackTraceElementFinder;

public class MethodOfCallerConverter extends MethodOfCallerConverterBase {
    public MethodOfCallerConverter() {
        super(ClassicStackTraceElementFinder.INSTANCE);
    }
}
