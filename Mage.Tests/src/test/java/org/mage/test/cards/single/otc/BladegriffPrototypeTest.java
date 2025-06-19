package org.mage.test.cards.single.otc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BladegriffPrototypeTest extends CardTestPlayerBase {
    private static final String griff = "Bladegriff Prototype";

    @Test
    public void test_CanTargetOwn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, griff);

        attack(1, playerA, griff);
        addTarget(playerB, "Squire");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Squire", 1);
    }

    @Test(expected=AssertionError.class)
    public void test_CantTargetYours() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, griff);

        attack(1, playerA, griff);
        addTarget(playerB, griff);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
    }

}
