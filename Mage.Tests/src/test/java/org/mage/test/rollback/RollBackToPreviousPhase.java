package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RollBackToPreviousPhase extends CardTestPlayerBase {

    @Test
    public void testNormalRollBack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 1);
        attack(3, playerA, "Llanowar Elves");
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        currentGame.rollbackToPreviousPhaseExecution();
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
