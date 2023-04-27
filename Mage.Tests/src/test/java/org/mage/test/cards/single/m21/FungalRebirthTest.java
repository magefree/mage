package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FungalRebirthTest extends CardTestPlayerBase {

    @Test
    public void createToken() {
        // Return target permanent card from your graveyard to your hand.
        // If a creature died this turn, create two 1/1 green Saproling creature tokens.
        addCard(Zone.HAND, playerA, "Fungal Rebirth");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Terror");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terror", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fungal Rebirth", "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Saproling Token", 2);
    }
}
