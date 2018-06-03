
package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ControlChangeTest extends CardTestMultiPlayerBase {

    /**
     * Checks that the change of max hand size changes if the control
     * of Jin-Gitaxias, Core Augur changes.
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=16910&p=177481#p177481
     */
    @Test
    public void testSimple() {
        // Each opponent's maximum hand size is reduced by seven.
        addCard(Zone.BATTLEFIELD, playerA, "Jin-Gitaxias, Core Augur");
        
        addCard(Zone.BATTLEFIELD, playerB, "Island",4);
        addCard(Zone.HAND, playerB, "Sower of Temptation");
        addCard(Zone.HAND, playerB, "Leyline of Anticipation");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sower of Temptation");
        addTarget(playerB, "Jin-Gitaxias, Core Augur");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20);

        assertPermanentCount(playerB, "Leyline of Anticipation", 1);

        assertHandCount(playerB, "Sower of Temptation", 0);
        assertGraveyardCount(playerB, "Sower of Temptation", 0);

        assertPermanentCount(playerB, "Jin-Gitaxias, Core Augur", 1);
        assertPermanentCount(playerB, "Sower of Temptation", 1);
        
        Assert.assertEquals(0, playerA.getMaxHandSize());
        Assert.assertEquals(7, playerB.getMaxHandSize());
        Assert.assertEquals(0, playerC.getMaxHandSize());
        Assert.assertEquals(7, playerD.getMaxHandSize());
    }

}