package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.internal.BackTrackingUtils;
import com.dwijnand.logright.internal.BackTrackingUtils.BackTrackingMatchBean;

public class BackTrackingLineOfCallerConverter extends LineOfCallerConverter {
    @Override
    public String convert(ILoggingEvent le) {
        BackTrackingMatchBean matchBean =
            BackTrackingUtils.findMatch(le, "line of caller", this);

        if (matchBean == null)
            return super.convert(le);

        return Integer.toString(matchBean.ste.getLineNumber());
    }
}
