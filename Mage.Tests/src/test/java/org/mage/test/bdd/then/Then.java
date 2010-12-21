package org.mage.test.bdd.then;

import org.mage.test.bdd.StepState;
import org.mage.test.bdd.and.Graveyards;

public class Then {
    public static Battlefield battlefield = new Battlefield(StepState.THEN);
    public static Graveyards graveyards = new Graveyards(StepState.THEN);
}
