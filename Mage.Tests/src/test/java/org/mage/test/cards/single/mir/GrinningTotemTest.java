package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GrinningTotemTest extends CardTestPlayerBase {

    @Test
    public void testCardsGoToGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grinning Totem");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}, Sacrifice {this}: Search target opponent's library for a card and exile it", playerB);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 1); // Grinning Totem
        assertGraveyardCount(playerB, 1); // the exiled Mountain
    }

}
