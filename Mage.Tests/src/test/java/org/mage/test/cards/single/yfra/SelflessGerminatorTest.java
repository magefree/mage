package org.mage.test.cards.single.yfra;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SelflessGerminatorTest extends CardTestPlayerBase {

    @Test
    public void testExhaustOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Selfless Germinator");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust");
        checkPlayableAbility("already exhausted", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Exhaust", false);
        checkPlayableAbility("still exhausted next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Seed of Hope", 1);
        assertHandCount(playerB, "Seed of Hope", 0);
    }

    @Test
    public void testBlinkResetsExhaust() {
        addCard(Zone.BATTLEFIELD, playerA, "Selfless Germinator");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Cloudshift");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Selfless Germinator");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Selfless Germinator", 1);
        assertHandCount(playerA, "Seed of Hope", 2);
    }
}
