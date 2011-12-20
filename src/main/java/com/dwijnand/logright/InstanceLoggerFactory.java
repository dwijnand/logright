package com.dwijnand.logright;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InstanceLoggerFactory {
    private InstanceLoggerFactory() {
        // Utility class
    }

    public static Logger getLogger(Class<?> runtimeClass, Class<?> staticClass) {
        return LoggerFactory.getLogger(getCompositeName(runtimeClass,
            staticClass));
    }

    public static Logger getLogger(ILoggerFactory iLoggerFactory,
        Class<?> runtimeClass, Class<?> staticClass) {
        return iLoggerFactory.getLogger(getCompositeName(runtimeClass,
            staticClass));
    }

    public static String getCompositeName(Class<?> runtimeClass,
        Class<?> staticClass) {
        return runtimeClass.getName() + "(" + staticClass.getName() + ")";
    }
}
