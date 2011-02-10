package org.mage.test.clientside.bdd.given;

import org.mage.test.clientside.bdd.StepState;

public class Have {
    public Have(StepState step) {
        a = new A(step);
    }
    public A a;
}