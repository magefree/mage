
package org.mage.test.cards.single.ths;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class OrdealEnchantmentsTest extends CardTestPlayerBase {

    @Test
    public void testOrdealofHeliod() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Enchant creature
        // Whenever enchanted creature attacks, put a +1/+1 counter on it. Then if it has three or more +1/+1 counters on it, sacrifice Ordeal of Heliod.
        // When you sacrifice Ordeal of Heliod, you gain 10 life.        
        addCard(Zone.HAND, playerB, "Ordeal of Heliod");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);


        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ordeal of Heliod", "Silvercoat Lion");
        
        attack(2, playerB, "Silvercoat Lion");
        attack(4, playerB, "Silvercoat Lion");
        attack(6, playerB, "Silvercoat Lion");

        setStopAt(6, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 8); // 3 + 4 + 5 = 12
        assertLife(playerB, 30);
        
        assertGraveyardCount(playerB, "Ordeal of Heliod", 1);
        
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 5,5);
    }

 
}