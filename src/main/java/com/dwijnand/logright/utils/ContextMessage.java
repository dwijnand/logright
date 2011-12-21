package com.dwijnand.logright.utils;

import ch.qos.logback.core.spi.ContextAware;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

class ContextMessage {
    static enum Level {
        INFO {
            @Override
            void logToContext(ContextAware ca, String msg) {
                ca.addInfo(msg);
            }

            @Override
            void addToContext(ContextAware ca, String msg, Throwable t) {
                ca.addInfo(msg, t);
            }
        },
        WARN {
            @Override
            void logToContext(ContextAware ca, String msg) {
                ca.addWarn(msg);
            }

            @Override
            void addToContext(ContextAware ca, String msg, Throwable t) {
                ca.addWarn(msg, t);
            }
        },
        ERROR {
            @Override
            void logToContext(ContextAware ca, String msg) {
                ca.addError(msg);
            }

            @Override
            void addToContext(ContextAware ca, String msg, Throwable t) {
                ca.addError(msg, t);
            }
        };

        abstract void logToContext(ContextAware ca, String msg);

        abstract void addToContext(ContextAware ca, String msg, Throwable t);
    }

    private final Level level;
    private final String messagePattern;
    private final Object[] args;

    public ContextMessage(Level level, String messagePattern, Object... args) {
        this.level = level;
        this.messagePattern = messagePattern;
        this.args = args;
    }

    public void logToContext(ContextAware ca) {
        FormattingTuple tuple =
            MessageFormatter.arrayFormat(messagePattern, args);
        String message = tuple.getMessage();
        Throwable throwable = tuple.getThrowable();
        if (throwable == null) {
            level.logToContext(ca, message);
        } else {
            level.addToContext(ca, message, throwable);
        }
    }
}
