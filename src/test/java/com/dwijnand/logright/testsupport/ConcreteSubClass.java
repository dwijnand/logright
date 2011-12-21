package com.dwijnand.logright.testsupport;

public class ConcreteSubClass extends ConcreteClass {
    public void concreteSubClassLogs() {
        LogMeSomething.log(logger, "The concrete subclass logs");
    }
}
