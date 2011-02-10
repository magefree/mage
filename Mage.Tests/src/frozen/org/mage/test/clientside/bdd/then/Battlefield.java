package org.mage.test.clientside.bdd.then;

import org.mage.test.clientside.base.MageAPI;
import org.mage.test.clientside.base.MageBase;
import org.mage.test.clientside.bdd.StepState;

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
