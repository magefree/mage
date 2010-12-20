package org.mage.test.bdd.and;

import org.mage.test.base.MageAPI;
import org.mage.test.base.MageBase;
import org.mage.test.bdd.StepState;
import org.mage.test.bdd.given.Have;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.mage.test.base.MageAPI.*;
import static org.mage.test.base.MageAPI.Owner.*;

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
