
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DethroneTest extends CardTestPlayerBase {

    @Test
    public void testAddingOnceACounterForEqualLife() {
        // Dethrone (Whenever this creature attacks the player with the most life or tied for most life, put a +1/+1 counter on it.)";
        // Other creatures you control have dethrone.
        // Whenever a creature you control with a +1/+1 counter on it dies, return that card to the battlefield under your control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Marchesa, the Black Rose"); // 3/3

        attack(1, playerA, "Marchesa, the Black Rose");

        attack(3, playerA, "Marchesa, the Black Rose");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 12); // 4 + 4

        assertCounterCount(playerA, "Marchesa, the Black Rose", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Marchesa, the Black Rose", 4, 4);

    }

}
