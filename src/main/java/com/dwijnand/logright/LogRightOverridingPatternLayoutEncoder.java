package com.dwijnand.logright;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

public class LogRightOverridingPatternLayoutEncoder extends
    PatternLayoutEncoderBase<ILoggingEvent> {
    @Override
    public void start() {
        PatternLayout logRightOverridingPatternLayout =
            new LogRightOverridingPatternLayout();
        logRightOverridingPatternLayout.setContext(context);
        logRightOverridingPatternLayout.setPattern(getPattern());
        logRightOverridingPatternLayout.start();
        layout = logRightOverridingPatternLayout;
        super.start();
    }
}
