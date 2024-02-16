package org.mage.test.clientside.bdd.and;

import org.junit.Assert;
import org.mage.test.clientside.base.MageAPI;
import org.mage.test.clientside.base.MageBase;
import org.mage.test.clientside.bdd.StepState;

import static junit.framework.TestCase.*;

public class Graveyards {
    private StepState step;
    public Graveyards(StepState step) {
        this.step = step;
    }
    public boolean empty() throws Exception {
        StepState current = MageAPI.defineStep(this.step);
        if (current.equals(StepState.THEN)) {
            boolean empty = MageBase.getInstance().checkGraveyardsEmpty();
            assertTrue(empty);
            return empty;
        } else {
            throw new AssertionError("Not implemented for step="+current);
        }
    }
}
