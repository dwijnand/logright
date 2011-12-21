package com.dwijnand.logright;

import com.dwijnand.logright.utils.ClassicStackTraceElementFinder;

public class FileOfCallerConverter extends FileOfCallerConverterBase {
    public FileOfCallerConverter() {
        super(ClassicStackTraceElementFinder.INSTANCE);
    }
}
