
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PowerSurgeTest extends CardTestPlayerBase {

    @Test
    public void testDamageInPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // At the beginning of each player's upkeep, Power Surge deals X damage to that player, where X is the number of untapped lands they controlled at the beginning of this turn.
        addCard(Zone.HAND, playerA, "Power Surge");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Power Surge");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 17);
    }
}
