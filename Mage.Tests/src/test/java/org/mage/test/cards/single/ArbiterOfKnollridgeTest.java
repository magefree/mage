package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ArbiterOfKnollridgeTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Constants.Zone.HAND, playerA, "Shock");
        addCard(Constants.Zone.HAND, playerA, "Arbiter of Knollridge");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Arbiter of Knollridge");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Arbiter of Knollridge", 1);
        assertGraveyardCount(playerA, 1);
    }
}
