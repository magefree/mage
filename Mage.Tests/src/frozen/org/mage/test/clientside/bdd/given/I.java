package org.mage.test.clientside.bdd.given;

import org.mage.test.clientside.bdd.StepState;

public class I {
    public I(StepState step) {
        have = new Have(step);
    }
    public Have have;
}