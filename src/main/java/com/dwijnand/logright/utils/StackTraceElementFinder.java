package com.dwijnand.logright.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import com.dwijnand.logright.utils.ContextMessage.Level;

import java.util.ArrayList;
import java.util.List;

public interface StackTraceElementFinder {
    Result find(ILoggingEvent le);

    public abstract class Result {
        protected Result() {
        }

        public abstract boolean found();

        public static ResultFound found(StackTraceElement ste) {
            return new ResultFound(ste, ste.getClassName());
        }

        public static ResultFound found(StackTraceElement ste,
            String classOfCaller) {
            return new ResultFound(ste, classOfCaller);
        }

        public static ResultNotFoundBuilder notFoundBuilder(String loggerName) {
            return new ResultNotFoundBuilder(loggerName);
        }

        public static class ResultFound extends Result {
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

        public static class ResultNotFound extends Result {
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

            public void logMessagesToContext(ContextAware ca) {
                for (ContextMessage contextMessage : contextMessages) {
                    contextMessage.logToContext(ca);
                }
            }
        }

        public static class ResultNotFoundBuilder {
            private final List<ContextMessage> contextMessages =
                new ArrayList<ContextMessage>();

            protected ResultNotFoundBuilder(String loggerName) {
                contextMessages.add(new ContextMessage(Level.WARN,
                    "Failed to find StackTraceElement for logger: {}",
                    loggerName));
            }

            public ResultNotFoundBuilder addCause(String cause) {
                contextMessages.add(new ContextMessage(Level.WARN, "Cause: {}",
                    cause));
                return this;
            }

            public ResultNotFoundBuilder addCallerData(
                StackTraceElement[] callerData) {

                attemptEnsueCapacity(contextMessages, callerData.length);
                for (int i = 0; i < callerData.length; i++) {
                    contextMessages.add(new ContextMessage(Level.INFO,
                        " callerData[{}]: {}", i, callerData[i]));
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

            private static <T> void attemptEnsueCapacity(List<T> list,
                int minCapacity) {
                if (list instanceof ArrayList) {
                    ((ArrayList<T>) list).ensureCapacity(minCapacity);
                }
            }
        }
    }
}
