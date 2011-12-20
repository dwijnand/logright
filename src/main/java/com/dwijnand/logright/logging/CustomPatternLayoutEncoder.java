package com.dwijnand.logright.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

import java.util.Map;

public class CustomPatternLayoutEncoder extends
    PatternLayoutEncoderBase<ILoggingEvent> {

    @Override
    public void start() {
        PatternLayout patternLayout = new PatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());

        Map<String, String> instanceConverterMap =
            patternLayout.getInstanceConverterMap();
        instanceConverterMap.put("L",
            BackTrackingLineOfCallerConverter.class.getName());
        instanceConverterMap.put("line",
            BackTrackingLineOfCallerConverter.class.getName());

        patternLayout.start();
        layout = patternLayout;
        super.start();
    }

}
