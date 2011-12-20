package com.dwijnand.logright.testsupport;

public class ConcreteClass extends AbstractClass {
    public void concreteClassLogs() {
        LogMeSomething.log(logger, "The concrete class logs");
    }
}
