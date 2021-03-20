
package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 * @author LevelX2
 */
public class CostModificationTest extends CardTestPlayerBaseAI {

    /**
     * There seems to be a problem when playing Fluctuator against Computer.
     * Once played, I am stuck at "Waiting for Computer" forever...
     */
    @Test
    public void testFluctuator() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fluctuator");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroma's Vengeance", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

}
