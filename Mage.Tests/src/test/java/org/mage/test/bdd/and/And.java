package org.mage.test.bdd.and;

import org.mage.test.bdd.StepState;
import org.mage.test.bdd.given.I;

public class And {
    public static Phase phase = new Phase(StepState.UNKNOWN);
    public static Graveyards graveyards = new Graveyards();
    public static I I = new I(StepState.UNKNOWN);
}
