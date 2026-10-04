package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BismuthMindrenderTest extends CardTestPlayerBase {

    @Test
    public void testCastOpponentsExiledCardByPayingLife() {
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Bismuth Mindrender");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");

        attack(1, playerA, "Bismuth Mindrender", playerB);
        setChoice(playerA, true); // Cast the exiled spell by paying life instead of mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 16);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }
}
