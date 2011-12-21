package com.dwijnand.logright.utils;

import ch.qos.logback.core.spi.ContextAware;

class ContextMessage {
    static enum Level {
        INFO {
            @Override
            void addToContext(ContextAware ca, String msg) {
                ca.addInfo(msg);
            }

            @Override
            void addToContext(ContextAware ca, String msg, Throwable t) {
                ca.addInfo(msg, t);
            }
        },
        WARN {
            @Override
            void addToContext(ContextAware ca, String msg) {
                ca.addWarn(msg);
            }

            @Override
            void addToContext(ContextAware ca, String msg, Throwable t) {
                ca.addWarn(msg, t);
            }
        },
        ERROR {
            @Override
            void addToContext(ContextAware ca, String msg) {
                ca.addError(msg);
            }

            @Override
            void addToContext(ContextAware ca, String msg, Throwable t) {
                ca.addError(msg, t);
            }
        };

        abstract void addToContext(ContextAware ca, String msg);

        abstract void addToContext(ContextAware ca, String msg, Throwable t);
    }

    private final Level level;
    private final String message;
    private Throwable throwable;

    public ContextMessage(Level level, String message, Throwable throwable) {
        this(level, message);
        this.throwable = throwable;
    }

    public ContextMessage(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    public void addToContext(ContextAware ca) {
        if (throwable == null) {
            level.addToContext(ca, message);
        } else {
            level.addToContext(ca, message, throwable);
        }
    }
}
