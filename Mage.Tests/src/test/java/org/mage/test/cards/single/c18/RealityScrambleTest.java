package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class RealityScrambleTest extends CardTestPlayerBase {

    @Test
    public void testFirstCardIsCast() {
        // Put target permanent you own on the bottom of your library. Reveal cards from the top of your library until
        // you reveal a card that shares a card type with that permanent. Put that card onto the battlefield and
        // the rest on the bottom of your library in a random order.
        addCard(Zone.HAND, playerA, "Reality Scramble");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, "Storm Crow");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reality Scramble", "Grizzly Bears");

        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Storm Crow", 1);
    }

}
