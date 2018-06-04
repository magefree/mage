
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GainedDiesTriggersTest extends CardTestPlayerBase {

    /**
     * Tests that gained dies triggers work as intended
     */
    @Test
    public void testInfernalScarring() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 3);

        // Enchant creature
        // Enchanted creature gets +2/+0 and has "When this creature dies, draw a card."
        addCard(Zone.HAND, playerA, "Infernal Scarring", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infernal Scarring", "Silvercoat Lion");

        // Destroy all creatures.
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerA, 1); // draw a card for dying Lion
    }

}
