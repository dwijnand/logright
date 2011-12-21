package com.dwijnand.logright.utils;

import ch.qos.logback.core.spi.ContextAware;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;

public abstract class StackTraceElementFinderResult {
    protected StackTraceElementFinderResult() {
    }

    public abstract boolean found();

    public static ResultFound found(StackTraceElement ste) {
        return new ResultFound(ste, ste.getClassName());
    }

    public static ResultFound
        found(StackTraceElement ste, String classOfCaller) {
        return new ResultFound(ste, classOfCaller);
    }

    public static ResultNotFoundBuilder notFoundBuilder(String loggerName) {
        return new ResultNotFoundBuilder(loggerName);
    }

    public static class ResultFound extends StackTraceElementFinderResult {
        private final StackTraceElement ste;
        private final String classOfCaller;

        protected ResultFound(StackTraceElement ste, String classOfCaller) {
            this.ste = ste;
            this.classOfCaller = classOfCaller;
        }

        @Override
        public boolean found() {
            return true;
        }

        public StackTraceElement getStackTraceElement() {
            return ste;
        }

        public String getClassOfCaller() {
            return classOfCaller;
        }
    }

    public static class ResultNotFound extends StackTraceElementFinderResult {
        private final String warnMessage;
        private final String[] infoMessages;

        protected ResultNotFound(String warnMessage, List<String> infoMessages) {
            this.warnMessage = warnMessage;
            this.infoMessages =
                infoMessages.toArray(new String[infoMessages.size()]);
        }

        @Override
        public boolean found() {
            return false;
        }

        public void addCause(ContextAware ca) {
            ca.addWarn(warnMessage);
            for (String infoMessage : infoMessages) {
                ca.addInfo(infoMessage);
            }
        }
    }

    public static class ResultNotFoundBuilder {
        private final String loggerName;
        private String cause;
        private final List<String> infoMessages = new ArrayList<String>();

        protected ResultNotFoundBuilder(String loggerName) {
            this.loggerName = loggerName;
        }

        public ResultNotFoundBuilder addCause(String cause) {
            this.cause = cause;
            return this;
        }

        public ResultNotFoundBuilder addCallerData(
            StackTraceElement[] callerData) {

            attemptEnsueCapacity(infoMessages, callerData.length);
            for (int i = 0; i < callerData.length; i++) {
                StackTraceElement ste = callerData[i];
                infoMessages.add(format(" callerData[{}]: {}", i, ste));
            }
            return this;
        }

        public ResultNotFound build() {
            return new ResultNotFound(getWarnMessage(), infoMessages);
        }

        private String getWarnMessage() {
            final String warnMessage;
            if (cause == null) {
                warnMessage =
                    format("Failed to find StackTraceElement for logger: {}",
                        loggerName);
            } else {
                warnMessage =
                    format("Failed to find StackTraceElement for logger: {} "
                        + "(cause: {})", loggerName, cause);
            }
            return warnMessage;
        }

        private static String format(String messagePattern, Object... args) {
            return MessageFormatter.arrayFormat(messagePattern, args)
                .getMessage();
        }

        private static <T> void attemptEnsueCapacity(List<T> list,
            int minCapacity) {
            if (list instanceof ArrayList) {
                ((ArrayList<T>) list).ensureCapacity(minCapacity);
            }
        }
    }
}
