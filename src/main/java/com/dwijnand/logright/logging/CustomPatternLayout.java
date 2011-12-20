package com.dwijnand.logright.logging;

import ch.qos.logback.classic.PatternLayout;

import java.util.Map;

// TODO Needed?
@Deprecated
public class CustomPatternLayout extends PatternLayout {
    public CustomPatternLayout() {
        Map<String, String> instanceConverterMap = getInstanceConverterMap();

        // instanceConverterMap.put("C",
        // ClassOfCallerConverter.class.getName());
        // instanceConverterMap.put("class",
        // ClassOfCallerConverter.class.getName());

        // instanceConverterMap.put("M",
        // MethodOfCallerConverter.class.getName());
        // instanceConverterMap.put("method",
        // MethodOfCallerConverter.class.getName());

        instanceConverterMap.put("L",
            BackTrackingLineOfCallerConverter.class.getName());
        instanceConverterMap.put("line",
            BackTrackingLineOfCallerConverter.class.getName());
    }
}
