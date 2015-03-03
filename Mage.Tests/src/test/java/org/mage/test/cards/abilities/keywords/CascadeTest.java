package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CascadeTest extends CardTestPlayerBase {
    
    /*
     * Maelstrom Nexus {WUBRG}
     * Enchantment
     * The first spell you cast each turn has cascade. (When you cast your first 
     * spell, exile cards from the top of your library until you exile a nonland 
     * card that costs less. You may cast it without paying its mana cost. Put 
     * the exiled cards on the bottom in a random order.)
     *
     * Predatory Advantage {3RG}
     * Enchantment
     * At the beginning of each opponent's end step, if that player didn't cast 
     * a creature spell this turn, put a 2/2 green Lizard creature token onto 
     * the battlefield.
    */
    
    // test that Predatory Advantage gains Cascade when cast
    @Test
    public void testGainsCascade() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Maelstrom Nexus");
        addCard(Zone.HAND, playerA, "Predatory Advantage");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Predatory Advantage");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Predatory Advantage", 1);
        assertPermanentCount(playerA, "Sejiri Merfolk", 1);
        assertPermanentCount(playerA, "Lizard", 1);
        
    }

    // test that 2nd spell cast (Nacatl Outlander) does not gain Cascade
    @Test
    public void testLosesCascade() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Maelstrom Nexus");
        addCard(Zone.HAND, playerA, "Predatory Advantage");
        addCard(Zone.HAND, playerA, "Nacatl Outlander");
        addCard(Zone.LIBRARY, playerA, "Arbor Elf");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Predatory Advantage");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Nacatl Outlander");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Predatory Advantage", 1);
        assertPermanentCount(playerA, "Sejiri Merfolk", 1);
        assertPermanentCount(playerA, "Nacatl Outlander", 1);
        assertPermanentCount(playerA, "Arbor Elf", 0);
        
    }
}
