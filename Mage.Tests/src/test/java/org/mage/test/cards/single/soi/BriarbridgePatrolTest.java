package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 3G
Creature - Human Warrior
Whenever Briarbridge Patrol deals damage to one or more creatures, investigate. (Put a colorless Clue artifact token onto the battlefield with "2, Sacrifice this artifact: Draw a card.")

At the beginning of each end step, if you sacrificed three or more clues this turn, you may put a creature card from your hand onto the battlefield. 
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class BriarbridgePatrolTest extends CardTestPlayerBase {
    
    /**
     * Deals damage to creature test.
     */
    @Test
    public void dealtDamageToCreatureInvestigate() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Briarbridge Patrol", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);
        
        attack(1, playerA, "Briarbridge Patrol");
        block(1, playerB, "Elite Vanguard", "Briarbridge Patrol");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue", 1);
        assertPermanentCount(playerA, "Briarbridge Patrol", 1);
        assertPermanentCount(playerB, "Elite Vanguard", 0);
        assertGraveyardCount(playerB, "Elite Vanguard", 1);
    }
}
