
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Checks that a dies triggered ability of a Token works
 *
 * @author LevelX2
 */
public class ReefWormTest extends CardTestPlayerBase {

    @Test
    public void testDiesTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        // When Reef Worm dies, put a 3/3 blue Fish creature token onto the battlefield with
        // "When this creature dies, put a 6/6 blue Whale creature token onto the battlefield with
        // ‘When this creature dies, put a 9/9 blue Kraken creature token onto the battlefield.'"
        addCard(Zone.BATTLEFIELD, playerB, "Reef Worm", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Reef Worm");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", "Fish Token");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Whale Token");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Whale Token");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Lightning Bolt", 4);

        assertPermanentCount(playerB, "Fish Token", 0);
        assertPermanentCount(playerB, "Whale Token", 0);
        assertPermanentCount(playerB, "Kraken Token", 1);
        assertGraveyardCount(playerB, "Reef Worm", 1);

        assertGraveyardCount(playerA, "Lightning Bolt", 4);
    }

}
