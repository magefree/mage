
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ServantOfTheScaleTest extends CardTestPlayerBase {

    /**
     * Tests that the dies triggered ability distributes
     * the +1/+1 counters on Servant of the Scale
     */
    @Test
    public void testDiesTriggeredAbility() {
        // Servant of the Scale enters the battlefield with a +1/+1 counter on it.
        // When Servant of the Scale dies, put X +1/+1 counters on target creature you control, where X is the number of +1/+1 counter on Servant of the Scale.
        addCard(Zone.HAND, playerA, "Servant of the Scale");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Servant of the Scale");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Servant of the Scale");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Servant of the Scale", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3,3);
    }
}
