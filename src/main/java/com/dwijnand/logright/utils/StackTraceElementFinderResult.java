package com.dwijnand.logright.utils;

import ch.qos.logback.core.spi.ContextAware;
import com.dwijnand.logright.utils.ContextMessage.Level;
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
        private final ContextMessage[] contextMessages;

        protected ResultNotFound(List<ContextMessage> contextMessages) {
            this.contextMessages =
                contextMessages.toArray(new ContextMessage[contextMessages
                    .size()]);
        }

        @Override
        public boolean found() {
            return false;
        }

        public void addCause(ContextAware ca) {
            for (ContextMessage contextMessage : contextMessages) {
                contextMessage.addToContext(ca);
            }
        }
    }

    public static class ResultNotFoundBuilder {
        private final List<ContextMessage> contextMessages =
            new ArrayList<ContextMessage>();

        protected ResultNotFoundBuilder(String loggerName) {
            String message =
                format("Failed to find StackTraceElement for logger: {}",
                    loggerName);
            contextMessages.add(new ContextMessage(Level.WARN, message));
        }

        public ResultNotFoundBuilder addCause(String cause) {
            String message = format("Cause: {}", cause);
            contextMessages.add(new ContextMessage(Level.WARN, message));
            return this;
        }

        public ResultNotFoundBuilder addCallerData(
            StackTraceElement[] callerData) {

            attemptEnsueCapacity(contextMessages, callerData.length);
            for (int i = 0; i < callerData.length; i++) {
                StackTraceElement ste = callerData[i];
                String message = format(" callerData[{}]: {}", i, ste);
                contextMessages.add(new ContextMessage(Level.INFO, message));
            }
            return this;
        }

        public ResultNotFoundBuilder addContextMessage(
            ContextMessage contextMessage) {
            contextMessages.add(contextMessage);
            return this;
        }

        public ResultNotFound build() {
            return new ResultNotFound(contextMessages);
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
