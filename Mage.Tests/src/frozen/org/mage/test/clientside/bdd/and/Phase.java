package org.mage.test.clientside.bdd.and;

import org.mage.test.clientside.base.MageAPI;
import org.mage.test.clientside.base.MageBase;
import org.mage.test.clientside.bdd.StepState;

import static org.mage.test.clientside.base.MageAPI.Owner.*;

public class Phase {
    private StepState step;
    public Phase(StepState step) {
        this.step = step;
    }
    public void is(String phase, MageAPI.Owner owner) throws Exception {
        StepState current = MageAPI.defineStep(this.step);
        if (current.equals(StepState.GIVEN)) {
            if ("Precombat Main".equals(phase) && (owner.equals(mine) || owner.equals(me))) {
                MageBase.getInstance().goToPhase("Precombat Main - play spells and sorceries.");
                return;
            }
            System.err.println("waitForPhase not implemented for phase="+phase+", owner="+owner.name());
            throw new RuntimeException("Not implemented.");
        } else {
            throw new RuntimeException("Not implemented for step = " + current);
        }
    }
}
