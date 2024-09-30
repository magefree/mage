package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TheMindskinnerTest extends CardTestPlayerBase {

    private static final String mindskinner = "The Mindskinner"; // 10/1 can't be blocked
    // If a source you control would deal damage to an opponent, prevent that damage and each opponent mills that many cards.

    private static final String piker = "Goblin Piker"; // 2/1

    private static final String bolt = "Lightning Bolt";

    @Test
    public void testPreventionAndMill() {
        addCard(Zone.BATTLEFIELD, playerA, mindskinner);
        addCard(Zone.BATTLEFIELD, playerA, piker);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        attack(1, playerA, piker, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, 1); // bolt
        assertGraveyardCount(playerB, 5);
    }

}
