
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SilenceTest extends CardTestPlayerBase {

    @Test
    public void testSilence() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Silence");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);
        
        castSpell(2, PhaseStep.UPKEEP, playerA, "Silence");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        
        execute();

        assertGraveyardCount(playerA, "Silence", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
    }       

}