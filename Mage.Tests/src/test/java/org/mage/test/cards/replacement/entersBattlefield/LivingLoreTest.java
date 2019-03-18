
package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LivingLoreTest extends CardTestPlayerBase {

    /**
     * That the +1/+1 counters are added to Living Lore before state based
     * actions take place
     */
    @Test
    public void testCountersAdded() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Living Lore"); //{3}{U}
        addCard(Zone.GRAVEYARD, playerA, "Natural Connection", 1); // {2}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Lore");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Living Lore", 1);
        assertPowerToughness(playerA, "Living Lore", 3, 3);
    }

}
