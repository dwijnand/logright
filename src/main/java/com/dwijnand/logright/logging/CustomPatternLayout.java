package com.dwijnand.logright.logging;

import ch.qos.logback.classic.PatternLayout;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomPatternLayout extends PatternLayout {
    public static final Map<String, String> defaultConverterMap;

    static {
        Map<String, String> defaultConverterMutableMap =
            new HashMap<String, String>(PatternLayout.defaultConverterMap);

        defaultConverterMutableMap.put("M",
            BackTrackingMethodOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("method",
            BackTrackingMethodOfCallerConverter.class.getName());

        defaultConverterMutableMap.put("L",
            BackTrackingLineOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("line",
            BackTrackingLineOfCallerConverter.class.getName());

        defaultConverterMap =
            Collections.unmodifiableMap(defaultConverterMutableMap);
    }

    @Override
    public Map<String, String> getDefaultConverterMap() {
        return defaultConverterMap;
    }
}
