package com.dwijnand.logright;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LogRightOverridingPatternLayout extends LogRightPatternLayout {
    public static final Map<String, String> defaultConverterMap;

    static {
        Map<String, String> defaultConverterMutableMap =
            new HashMap<String, String>(
                LogRightPatternLayout.defaultConverterMap);

        defaultConverterMutableMap.put("C",
            BackTrackingClassOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("class",
            BackTrackingClassOfCallerConverter.class.getName());

        defaultConverterMutableMap.put("M",
            BackTrackingMethodOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("method",
            BackTrackingMethodOfCallerConverter.class.getName());

        defaultConverterMutableMap.put("L",
            BackTrackingLineOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("line",
            BackTrackingLineOfCallerConverter.class.getName());

        defaultConverterMutableMap.put("F",
            BackTrackingFileOfCallerConverter.class.getName());
        defaultConverterMutableMap.put("file",
            BackTrackingFileOfCallerConverter.class.getName());

        defaultConverterMap =
            Collections.unmodifiableMap(defaultConverterMutableMap);
    }

    @Override
    public Map<String, String> getDefaultConverterMap() {
        return defaultConverterMap;
    }
}
