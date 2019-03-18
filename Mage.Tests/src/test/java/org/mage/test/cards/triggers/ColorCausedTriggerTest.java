
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ColorCausedTriggerTest extends CardTestPlayerBase {

    @Test
    public void testGhostfire() {
        // Whenever a player casts a red spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon's Claw", 1);

        // Ghostfire deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Ghostfire", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ghostfire", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Ghostfire", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

}
