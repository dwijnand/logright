package com.dwijnand.logright;

import com.dwijnand.logright.utils.ClassicStackTraceElementFinder;

public class ClassOfCallerConverter extends ClassOfCallerConverterBase {
    public ClassOfCallerConverter() {
        super(ClassicStackTraceElementFinder.INSTANCE);
    }
}
