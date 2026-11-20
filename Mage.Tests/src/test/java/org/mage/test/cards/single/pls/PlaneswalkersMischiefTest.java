package org.mage.test.cards.single.pls;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PlaneswalkersMischiefTest extends CardTestPlayerBase {

    @Test
    public void testReturnsExiledCardToHand() {

        addCard(Zone.BATTLEFIELD, playerA, "Planeswalker's Mischief");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}: Target opponent reveals", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Lightning Bolt", 0);
        assertHandCount(playerB, "Lightning Bolt", 1);
    }
}
