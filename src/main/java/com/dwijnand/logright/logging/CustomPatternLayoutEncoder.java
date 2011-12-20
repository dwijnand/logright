package com.dwijnand.logright.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

// TODO: Rename
public class CustomPatternLayoutEncoder extends
    PatternLayoutEncoderBase<ILoggingEvent> {

    @Override
    public void start() {
        PatternLayout patternLayout = new CustomPatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());

        patternLayout.start();
        layout = patternLayout;
        super.start();
    }

}
