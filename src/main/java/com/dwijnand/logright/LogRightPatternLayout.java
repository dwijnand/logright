package com.dwijnand.logright;

import ch.qos.logback.classic.PatternLayout;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LogRightPatternLayout extends PatternLayout {
    public static final Map<String, String> defaultConverterMap;

    static {
        Map<String, String> defaultConverterMutableMap =
            new HashMap<String, String>(PatternLayout.defaultConverterMap);

        defaultConverterMutableMap.put("RC",
            BackTrackingClassOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("rightclass",
            BackTrackingClassOfCallerConverter.class.getName());

        defaultConverterMutableMap.put("RM",
            BackTrackingMethodOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("rightmethod",
            BackTrackingMethodOfCallerConverter.class.getName());

        defaultConverterMutableMap.put("RL",
            BackTrackingLineOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("rightline",
            BackTrackingLineOfCallerConverter.class.getName());

        defaultConverterMutableMap.put("RF",
            BackTrackingFileOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("rightfile",
            BackTrackingFileOfCallerConverter.class.getName());

        defaultConverterMap =
            Collections.unmodifiableMap(defaultConverterMutableMap);
    }

    @Override
    public Map<String, String> getDefaultConverterMap() {
        return defaultConverterMap;
    }
}
