package com.dwijnand.logright;

import com.dwijnand.logright.testsupport.ConcreteSubClass;
import com.dwijnand.logright.testsupport.SimpleStatelessClass;

public class Main {
    public static void main(String[] args) {
        SimpleStatelessClass.simpleStatelessClassLogs();

        ConcreteSubClass c = new ConcreteSubClass();
        c.concreteSubClassLogs();
        c.concreteClassLogs();
        c.abstractClassLogs();
    }
}
