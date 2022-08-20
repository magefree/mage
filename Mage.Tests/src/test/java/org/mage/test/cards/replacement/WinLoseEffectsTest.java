
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class WinLoseEffectsTest extends CardTestPlayerBase {

    /**
     * When Platinum Angel and Laboratory Maniac are on the same side of the field, you can't win by 
     * drawing with no cards left in the library. It seems that Laboratory Maniac is replacing the 
     * game loss rather than the card draw.
     */
    @Test
    public void testPlatinumAngelAndLaboratoryManiac() {
        // Platinum Angel {7} - Flying  - 4/4
        // You can't lose the game and your opponents can't win the game.
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Angel");
        // Laboratory Maniac {2}{U} - Creature Wizard - 2/3
        // If you would draw a card while your library has no cards in it, you win the game instead.
        addCard(Zone.BATTLEFIELD, playerA, "Laboratory Maniac", 1);
        // If you would draw a card, draw two cards instead. 
        addCard(Zone.BATTLEFIELD, playerA, "Thought Reflection", 4);

        setStopAt(40, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals("Player A library is empty", 0 , playerA.getLibrary().size());
        assertWonTheGame(playerA);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
    
    /*
     * Reported bug: My library was empty and I controlled Platinum Angel. I had already "drawn" from an empty library but hadn't lost due to the angel's effect. 
     * My opponent cast Set Adrift on the Angel, and I lost immediately when it resolved.
    */
    @Test
    public void testPlatinumAngelBouncedWithEmptyLibrary() {
        
        // Platinum Angel {7} - Flying  - 4/4
        // You can't lose the game and your opponents can't win the game. 
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Angel");    
        
        // Set Adrift {5}{U} - Sorcery
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}
        // Put target nonland permanent on top of its owner's library.
        addCard(Zone.HAND, playerB, "Set Adrift", 1);        
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Set Adrift");
        // Platinum Angel is auto-chosen since only possible target
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Set Adrift", 1);
        assertPermanentCount(playerA, "Platinum Angel", 0);
        assertLibraryCount(playerA, 1); // Angel returned to top of library
        assertLibraryCount(playerA, "Platinum Angel", 1);
        assertHasNotLostTheGame(playerA);
    }
    
    /**
     * If I have resolved an Angel's Grace this turn, have an empty library, a Laboratory Maniac on 
     * the battlefield, and would draw a card, nothing happens. I should win the game if the card drawing effect resolves.
     */
    @Test
    public void testAngelsGrace() {
        addCard(Zone.HAND, playerA, "Angel's Grace");
        // Prevent the next 1 damage that would be dealt to any target this turn.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Bandage");
        
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Laboratory Maniac", 1);
        
        skipInitShuffling();
        
        playerA.getLibrary().clear();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel's Grace");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bandage");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals("Player A library is empty", 0 , playerA.getLibrary().size());
        assertWonTheGame(playerA);
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
    
   @Test
    public void testAngelsGrace2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Instant {W}
        // You can't lose the game this turn and your opponents can't win the game this turn. Until end of turn, damage that would reduce your life total to less than 1 reduces it to 1 instead.        
        addCard(Zone.HAND, playerA, "Angel's Grace");
        // Instant - {3}{B}{B}
        // Reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost. 
        // You may repeat this process any number of times.        
        addCard(Zone.HAND, playerA, "Ad Nauseam");
        
        // Creature
        // If you would draw a card while your library has no cards in it, you win the game instead.
        addCard(Zone.BATTLEFIELD, playerA, "Laboratory Maniac", 1);
        
        skipInitShuffling();
        
        playerA.getLibrary().clear();
        // Instant {U}
        // Draw a card. Scry 2 
        addCard(Zone.LIBRARY, playerA, "Serum Visions"); // 1 life lost
        addCard(Zone.LIBRARY, playerA, "Bogardan Hellkite", 3); // 24 life lost

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel's Grace");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ad Nauseam");
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, true);
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Serum Visions");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Angel's Grace", 1);
        assertGraveyardCount(playerA, "Ad Nauseam", 1);
        assertGraveyardCount(playerA, "Serum Visions", 1);
        
        Assert.assertEquals("Player A library is empty", 0 , playerA.getLibrary().size());

        assertLife(playerA, -5);
        assertLife(playerB, 20);

        assertWonTheGame(playerA);

    }    
}
