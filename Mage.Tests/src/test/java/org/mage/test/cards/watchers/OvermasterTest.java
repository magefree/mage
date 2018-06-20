package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class OvermasterTest extends CardTestPlayerBase {
    /*
     * Overmaster
     * Sorcery, R (1)
     * The next instant or sorcery spell you cast this turn can't be countered.
     * Draw a card.
     *
    */
    
    // test that next spell can't be countered
    @Test
    public void testCantCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Counterspell");
        addCard(Zone.HAND, playerA, "Overmaster");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Overmaster");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Raging Goblin");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Counterspell", "Lightning Bolt");        
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
            
        assertPermanentCount(playerA, "Raging Goblin", 0);
        assertGraveyardCount(playerA, "Raging Goblin", 1);
    }

    // test that second spell can be countered
    @Test
    public void testCanCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerA, "Counterspell");
        addCard(Zone.HAND, playerA, "Overmaster");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Overmaster");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Lightning Bolt", "Raging Goblin");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Raging Goblin");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Counterspell", "Lightning Bolt");        
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
            
        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertGraveyardCount(playerA, "Raging Goblin", 1);
    }
    
}
