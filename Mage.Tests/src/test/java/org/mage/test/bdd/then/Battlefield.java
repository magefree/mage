package org.mage.test.bdd.then;

import org.mage.test.base.MageAPI;
import org.mage.test.base.MageBase;
import org.mage.test.bdd.StepState;

public class Battlefield {
    private StepState step;
    public Battlefield(StepState step) {
        this.step = step;
    }
    public boolean has(String cardName) throws Exception {
        StepState current = MageAPI.defineStep(this.step);
        if (current.equals(StepState.THEN)) {
            return MageBase.getInstance().checkBattlefield(cardName);
        } else {
            throw new AssertionError("Not implemented for step="+current);
        }
    }
}
