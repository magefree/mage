package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ArbiterOfKnollridgeTest extends CardTestPlayerBase {

    @Test
    @Ignore
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Arbiter of Knollridge");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Arbiter of Knollridge");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Arbiter of Knollridge", 1);
        assertGraveyardCount(playerA, 1);
    }
}
