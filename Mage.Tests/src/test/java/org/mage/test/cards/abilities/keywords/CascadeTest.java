package org.mage.test.cards.abilities.keywords;


import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
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

    // test that player does not lose if all cards are exiled by cascade
    // If you cast a spell with cascade and there are no nonland cards in your library with a converted mana
    // cost that's less that that spell's converted mana cost, you'll exile your entire library. Then you'll
    // randomly rearrange those cards and put them back as your library. Although you're essentially shuffling
    // those cards, you're not technically doing so; abilities that trigger whenever you shuffle your library
    // won't trigger.
    @Test
    public void testEmptyLibraryCascasde() {
        
        playerA.getLibrary().clear();

        addCard(Zone.LIBRARY, playerA, "Plains", 10);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Ardent Plea - Enchantment {1}{W}{U}
        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        // Cascade
        addCard(Zone.HAND, playerA, "Ardent Plea");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ardent Plea");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ardent Plea", 1);
        // the 10 lands go back to library
        Assert.assertEquals("The 10 lands went back to library", 10, playerA.getLibrary().size());
        Assert.assertTrue("Player A is still in game", playerA.isInGame());
    }

    @Test
    public void testEmptyLibraryCascasdeNexus() {

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Maelstrom Nexus");

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Aven Skirmisher - Creature {W}
        addCard(Zone.HAND, playerA, "Aven Skirmisher");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aven Skirmisher");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Maelstrom Nexus", 1);
        Assert.assertTrue("Player A is still in game", playerA.isInGame());
        assertHandCount(playerA, "Aven Skirmisher", 0);
        assertPermanentCount(playerA, "Aven Skirmisher", 1);
        // the 2 lands go back to library
        Assert.assertEquals("The 2 lands went back to library", 2, playerA.getLibrary().size());
        Assert.assertTrue("Player A is still in game", playerA.isInGame());
        
    }

}
