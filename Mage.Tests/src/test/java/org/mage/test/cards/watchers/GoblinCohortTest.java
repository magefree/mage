package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class GoblinCohortTest extends CardTestPlayerBase {
    /*
     * Goblin Cohort
     * Creature — Goblin Warrior 2/2, R (1)
     * Goblin Cohort can't attack unless you've cast a creature spell this turn.
     *
    */
    
    // test that Goblin Cohort can attack
    @Test
    public void testCanAttack() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Cohort");
        addCard(Zone.HAND, playerB, "Goblin Roughrider");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Goblin Roughrider");
        attack(2, playerB, "Goblin Cohort");
        
        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
                
        assertAttacking("Goblin Cohort", true);
        
    }
    
    // test that Goblin Cohort can't attack
    @Test
    public void testCantAttack() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Cohort");
        addCard(Zone.HAND, playerB, "Goblin Roughrider");
        
        attack(2, playerB, "Goblin Cohort");
        
        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
                
        assertAttacking("Goblin Cohort", false);
        
    }

}
