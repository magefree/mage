
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HumilityTest extends CardTestPlayerBase {

    /**
     * During a commander game both were on the battlefield, and Masumaro's P/T
     * was not displaying as 1/1.
     */
    @Test
    public void testHumilityAndMasumaro() {

        // Masumaro, First to Live's power and toughness are each equal to twice the number of cards in your hand.
        addCard(Zone.BATTLEFIELD, playerB, "Masumaro, First to Live");

        // Enchantment  {2}{W}{W}
        // All creatures lose all abilities and are 1/1.
        addCard(Zone.BATTLEFIELD, playerA, "Humility");

        addCard(Zone.HAND, playerB, "Plains", 3);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerB, "Masumaro, First to Live", 1, 1);

    }
}
