package com.dwijnand.logright;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

public class LogRightPatternLayoutEncoder extends
    PatternLayoutEncoderBase<ILoggingEvent> {
    @Override
    public void start() {
        PatternLayout logRightPatternLayout = new LogRightPatternLayout();
        logRightPatternLayout.setContext(context);
        logRightPatternLayout.setPattern(getPattern());
        logRightPatternLayout.start();
        layout = logRightPatternLayout;
        super.start();
    }
}
