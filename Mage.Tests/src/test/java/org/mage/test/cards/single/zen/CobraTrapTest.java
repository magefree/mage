package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CobraTrapTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Cobra Trap");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Stone Rain");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Stone Rain", "Forest");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cobra Trap");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Snake Token", 4);
    }

    @Test
    public void testCardNegative() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Cobra Trap");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Stone Rain");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cobra Trap");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Forest", 2);
        assertPermanentCount(playerA, "Snake Token", 0);
    }

}
