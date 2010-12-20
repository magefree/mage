package org.mage.test.bdd.given;

import org.mage.test.bdd.StepController;
import org.mage.test.bdd.StepState;

public class I {
    public I(StepState step) {
        have = new Have(step);
    }
    public Have have;
}