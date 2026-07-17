package org.mage.test.cards.single.acr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ShayCormacTest extends CardTestPlayerBase {

    @Test
    public void testRemoveWard() {
        addCard(Zone.BATTLEFIELD, playerA, "Shay Cormac");
        addCard(Zone.BATTLEFIELD, playerB, "Tomakul Honor Guard");
        addCard(Zone.HAND, playerA, "Fell");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fell", "Tomakul Honor Guard");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Tomakul Honor Guard", 0);
        assertGraveyardCount(playerB, "Tomakul Honor Guard", 1);
    }

}
