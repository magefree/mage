package org.mage.test.bdd.given;

import org.mage.test.bdd.StepController;
import org.mage.test.bdd.StepState;

public class Have {
    public Have(StepState step) {
        a = new A(step);
    }
    public A a;
}