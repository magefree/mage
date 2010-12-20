package org.mage.test.bdd.given;

import org.mage.test.bdd.StepState;
import org.mage.test.bdd.and.Phase;

public class Given {
    public static I I = new I(StepState.GIVEN);
    public static Phase phase;
}
