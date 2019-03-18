package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;



/**
 *
 * @author LevelX2
 */

public class FecundityTest extends CardTestPlayerBase {

    /**
     * 
     */
    @Test
    public void testOpponentDrawsACard() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        // Whenever a creature dies, that creature's controller may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Fecundity", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertHandCount(playerA, 1);
    }

}
