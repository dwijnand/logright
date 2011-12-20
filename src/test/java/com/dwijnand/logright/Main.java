package com.dwijnand.logright;

import com.dwijnand.logright.testsupport.ConcreteClass;
import com.dwijnand.logright.testsupport.SimpleStatelessClass;

public class Main {
    public static void main(String[] args) {
        SimpleStatelessClass.simpleStatelessClassLogs();

        ConcreteClass concreteClass = new ConcreteClass();
        concreteClass.concreteClassLogs();
        concreteClass.abstractClassLogs();
    }
}
