
package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 *
 * @author LevelX2
 */
public class CastAlternateCastingCostsTest extends CardTestPlayerBaseAI {

    /**
     * Tests that if a spell has alternate casting costs, this option is also calculated
     */
    @Test
    @Ignore // AI only gets the cast ability yet, but does always say yes to use evoke by default
    public void testEvoke() {
        // Flying
        // When Mulldrifter enters the battlefield, draw two cards.
        // Evoke (You may cast this spell for its evoke cost. If you do, it's sacrificed when it enters the battlefield.)
        addCard(Zone.HAND, playerA, "Mulldrifter");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mulldrifter", 1);
    }
    
}

