
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SunderingTitanTest extends CardTestPlayerBase {

    /**
     * the card Sundering Titan doesn't trigger for the aposing player
     *
     */
    @Test
    public void testComesIntoTriggeredAbility() {
        // // When Sundering Titan enters the battlefield or leaves the battlefield, choose a land of each basic land type, then destroy those lands.
        addCard(Zone.HAND, playerA, "Sundering Titan"); // {8}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sundering Titan");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sundering Titan", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Island", 1);

        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerB, "Plains", 1);

    }

}
