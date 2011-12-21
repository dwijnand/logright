package com.dwijnand.logright;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.dwijnand.logright.internal.BackTrackingUtils;
import com.dwijnand.logright.internal.BackTrackingUtils.BackTrackingMatchBean;

// TODO: Figure out if there is a way to support the Abbreviator better
public class BackTrackingClassOfCallerConverter extends ClassOfCallerConverter {
    @Override
    protected String getFullyQualifiedName(ILoggingEvent le) {
        BackTrackingMatchBean matchBean =
            BackTrackingUtils.findMatch(le, "class of caller", this);

        if (matchBean == null)
            return super.getFullyQualifiedName(le);

        return matchBean.classOfCaller;
    }
}
