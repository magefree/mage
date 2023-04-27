
package org.mage.test.cards.single.lgn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author cg5
 */
public class InfernalCaretakerTest extends CardTestPlayerBase {
    
    @Test
    /*
     * Infernal Caretaker {3}{B}
     * Creature - Human Cleric
     * Morph {3}{B}
     * When Infernal Caretaker is turned face up, return all Zombie cards from
     * all graveyards to their owners' hands.
     */
    public void testInfernalCaretaker() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Walking Corpse", 1);
        addCard(Zone.HAND, playerA, "Infernal Caretaker", 1);
        addCard(Zone.GRAVEYARD, playerA, "Walking Corpse", 4);
        addCard(Zone.GRAVEYARD, playerA, "Storm Crow", 4);
        
        addCard(Zone.GRAVEYARD, playerB, "Festering Goblin", 4);
        addCard(Zone.GRAVEYARD, playerB, "Elvish Visionary", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infernal Caretaker", true);
        setChoice(playerA, true); // Cast as a morph
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{B}: Turn this face-down permanent face up.");
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Infernal Caretaker", 1);
        assertPermanentCount(playerA, "Walking Corpse", 1);
        assertHandCount(playerA, "Walking Corpse", 4);
        assertHandCount(playerA, "Storm Crow", 0);
        assertHandCount(playerA, "Festering Goblin", 0);
        assertHandCount(playerA, "Elvish Visionary", 0);
        assertGraveyardCount(playerA, 4); // 4 * Storm Crow
        
        assertPermanentCount(playerB, "Infernal Caretaker", 0);
        assertPermanentCount(playerB, "Walking Corpse", 0);
        assertHandCount(playerB, "Walking Corpse", 0);
        assertHandCount(playerB, "Storm Crow", 0);
        assertHandCount(playerB, "Festering Goblin", 4);
        assertHandCount(playerB, "Elvish Visionary", 0);
        assertGraveyardCount(playerB, 4); // 4 * Elvish Visionary
    }
}
