package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.MethodOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.internal.BackTrackingUtils;
import com.dwijnand.logright.internal.BackTrackingUtils.BackTrackingMatchBean;

public class BackTrackingMethodOfCallerConverter extends
    MethodOfCallerConverter {

    @Override
    public String convert(ILoggingEvent le) {
        BackTrackingMatchBean matchBean =
            BackTrackingUtils.findMatch(le, "method of caller", this);

        if (matchBean == null)
            return super.convert(le);

        return matchBean.ste.getMethodName();
    }
}
