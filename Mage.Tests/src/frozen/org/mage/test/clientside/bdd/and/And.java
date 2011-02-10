package org.mage.test.clientside.bdd.and;

import org.mage.test.clientside.bdd.StepState;
import org.mage.test.clientside.bdd.given.I;

public class And {
    public static Phase phase = new Phase(StepState.UNKNOWN);
    public static Graveyards graveyards = new Graveyards(StepState.UNKNOWN);
    public static I I = new I(StepState.UNKNOWN);
}
