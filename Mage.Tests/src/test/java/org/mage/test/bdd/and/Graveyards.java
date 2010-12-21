package org.mage.test.bdd.and;

import org.junit.Assert;
import org.mage.test.base.MageAPI;
import org.mage.test.base.MageBase;
import org.mage.test.bdd.StepState;

import static org.hamcrest.core.Is.is;

public class Graveyards {
    private StepState step;
    public Graveyards(StepState step) {
        this.step = step;
    }
    public boolean empty() throws Exception {
        StepState current = MageAPI.defineStep(this.step);
        if (current.equals(StepState.THEN)) {
            boolean empty = MageBase.getInstance().checkGraveyardsEmpty();
            Assert.assertThat(empty, is(true));
            return empty;
        } else {
            throw new AssertionError("Not implemented for step="+current);
        }
    }
}
