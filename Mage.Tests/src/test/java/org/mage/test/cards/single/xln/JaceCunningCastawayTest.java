package org.mage.test.cards.single.xln;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class JaceCunningCastawayTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.j.JaceCunningCastaway Jace, Cunning Castaway} {1}{U}{U}
     * Legendary Planeswalker — Jace
     * +1: Whenever one or more creatures you control deal combat damage to a player this turn, draw a card, then discard a card.
     * −2: Create a 2/2 blue Illusion creature token with “When this creature becomes the target of a spell, sacrifice it.”
     * −5: Create two tokens that are copies of Jace, Cunning Castaway, except they’re not legendary.
     * Loyalty: 3
     */
    private static final String jace = "Jace, Cunning Castaway";

    @Test
    public void test_PlusOne_Trigger() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        addCard(Zone.BATTLEFIELD, playerA, "Alaborn Trooper");
        addCard(Zone.LIBRARY, playerA, "Taiga"); // for looting.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");

        attack(1, playerA, "Savannah Lions", playerB);
        attack(1, playerA, "Alaborn Trooper", playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2 - 2);
        assertGraveyardCount(playerA, "Taiga", 1);
    }
}
