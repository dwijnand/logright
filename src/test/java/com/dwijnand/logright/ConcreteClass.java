package com.dwijnand.logright;

public class ConcreteClass extends AbstractClass {
    public void concreteClassLogs() {
        LogMeSomething.log(logger, "The concrete class logs");
    }
}
