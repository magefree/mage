package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author drmDev
 */
public class FootlightFiendTest extends CardTestPlayerBase {
    
    @Test
    public void test_GrizzlyBearBlocksFootlightFiend_BothDie()
    {
        addCard(Zone.BATTLEFIELD, playerA, "Footlight Fiend"); // (R/B) 1/1 on death pings any target for 1
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // (G) 2/2
        
        attack(1, playerA, "Footlight Fiend");
        block(1, playerB, "Grizzly Bears", "Footlight Fiend");
        addTarget(playerA, "Grizzly Bears");
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Footlight Fiend", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}