package org.mage.test.clientside.bdd.then;

import org.mage.test.clientside.bdd.StepState;
import org.mage.test.clientside.bdd.and.Graveyards;

public class Then {
    public static Battlefield battlefield = new Battlefield(StepState.THEN);
    public static Graveyards graveyards = new Graveyards(StepState.THEN);
}
