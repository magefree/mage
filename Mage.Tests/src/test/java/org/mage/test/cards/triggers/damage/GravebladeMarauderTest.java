
package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GravebladeMarauderTest extends CardTestPlayerBase {

    @Test
    public void testTwoAttackers() {
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 3);

        // Whenever Graveblade Marauder deals combat damage to a player, that player loses life equal to the number of creature cards in your graveyard.
        addCard(Zone.BATTLEFIELD, playerB, "Graveblade Marauder", 2);//  1/4

        attack(2, playerB, "Graveblade Marauder");
        attack(2, playerB, "Graveblade Marauder");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 12); // 1 + 3  + 1 + 3 = 8
        assertLife(playerB, 20);
    }

}
