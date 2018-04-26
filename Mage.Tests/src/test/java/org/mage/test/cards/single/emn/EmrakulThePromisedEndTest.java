package org.mage.test.cards.single.emn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class EmrakulThePromisedEndTest extends CardTestPlayerBase {

    // Test that extra turn is added correctly when Emrakul is cast during an opponent's turn.
    @Test
    public void testExtraTurn_Turn2() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 20);
        // Creature cards you own that aren't on the battlefield have flash.
        addCard(Zone.HAND, playerB, "Teferi, Mage of Zhalfir");
        // When you cast Emrakul, you gain control of target opponent during that player's next turn.
        // After that turn, that player takes an extra turn.
        addCard(Zone.HAND, playerB, "Emrakul, the Promised End");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Teferi, Mage of Zhalfir");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Emrakul, the Promised End");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertActivePlayer(playerB);
    }

    @Test
    public void testExtraTurn_Turn3() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 20);
        addCard(Zone.HAND, playerB, "Teferi, Mage of Zhalfir");
        addCard(Zone.HAND, playerB, "Emrakul, the Promised End");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Teferi, Mage of Zhalfir");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Emrakul, the Promised End");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertActivePlayer(playerA);
    }

    @Test
    public void testExtraTurn_Turn4() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 20);
        addCard(Zone.HAND, playerB, "Teferi, Mage of Zhalfir");
        addCard(Zone.HAND, playerB, "Emrakul, the Promised End");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Teferi, Mage of Zhalfir");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Emrakul, the Promised End");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertActivePlayer(playerA);
    }

}
