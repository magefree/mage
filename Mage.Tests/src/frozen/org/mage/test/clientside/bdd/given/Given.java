package org.mage.test.clientside.bdd.given;

import org.mage.test.clientside.bdd.StepState;
import org.mage.test.clientside.bdd.and.Phase;

public class Given {
    public static I I = new I(StepState.GIVEN);
    public static Phase phase = new Phase(StepState.GIVEN);
}
