
package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class RemoveCounterCostTest extends CardTestPlayerBase {

    @Test
    public void testNovijenSages() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // Graft 4
        // {1}, Remove two +1/+1 counters from among creatures you control: Draw a card.        
        addCard(Zone.HAND, playerA, "Novijen Sages");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Novijen Sages", true);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Remove two +1/+1 counters");
        setChoice(playerA, "X=2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Novijen Sages", 1);
        assertPowerToughness(playerA, "Novijen Sages", 2, 2);

        assertHandCount(playerA, 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
   
}
