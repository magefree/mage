package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class AggravateTest extends CardTestPlayerBase {
    /*
     * Aggravate
     * Instant, 3RR (5)
     * Aggravate deals 1 damage to each creature target player controls. Each 
     * creature dealt damage this way attacks this turn if able.
     *
    */
    
    // test that creatures damaged by Aggravate attack
    @Test
    public void testDamagedCreaturesAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Aggravate");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Aggravate", playerB);
        
        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
                
        assertAttacking("Craw Wurm", true);
        assertAttacking("Goblin Roughrider", true);
        
    }
    
    // test that creatures not damaged by Aggravate don't attack
    @Test
    public void testUndamagedCreaturesDontAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Aggravate");
        addCard(Zone.HAND, playerB, "Raging Goblin");
        
        castSpell(2, PhaseStep.UPKEEP, playerA, "Aggravate", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Raging Goblin");
        
        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
                
        assertAttacking("Craw Wurm", true);
        assertAttacking("Goblin Roughrider", true);
        assertAttacking("Raging Goblin", false);
        
    }

}
