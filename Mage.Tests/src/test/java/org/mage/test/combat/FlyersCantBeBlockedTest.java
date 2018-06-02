

package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test that flyers can't be blocked if blocker has no flying and no reach ability
 * 
 * @author LevelX2
 */
public class FlyersCantBeBlockedTest extends CardTestPlayerBase {

    @Test
    public void testFlyingVsNonFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Necropede");
        addCard(Zone.BATTLEFIELD, playerB, "Pilgrim's Eye");
        addCard(Zone.HAND, playerB, "Pilgrim's Eye",2);
        addCard(Zone.LIBRARY, playerB, "Pilgrim's Eye",2);

        attack(2, playerB, "Pilgrim's Eye");
        block(2, playerA, "Necropede", "Pilgrim's Eye");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);

        Permanent pilgrimsEye = getPermanent("Pilgrim's Eye", playerB.getId());
        Assert.assertTrue("Should be tapped because of attacking", pilgrimsEye.isTapped());
    }    
    
    @Test
    public void testFlyingVsNonFlying2() {
        addCard(Zone.BATTLEFIELD, playerA, "Necropede");
        addCard(Zone.BATTLEFIELD, playerB, "Pilgrim's Eye");
        addCard(Zone.LIBRARY, playerA, "Pilgrim's Eye");
        addCard(Zone.HAND, playerA, "Pilgrim's Eye");
        addCard(Zone.GRAVEYARD, playerA, "Pilgrim's Eye");
        
        attack(2, playerB, "Pilgrim's Eye");
        block(2, playerA, "Necropede", "Pilgrim's Eye");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);

        Permanent pilgrimsEye = getPermanent("Pilgrim's Eye", playerB.getId());
        Assert.assertTrue("Should be tapped because of attacking", pilgrimsEye.isTapped());
    }    
        
    
}
