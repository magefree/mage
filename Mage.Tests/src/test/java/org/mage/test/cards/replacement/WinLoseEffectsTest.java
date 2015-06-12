/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Angel");
        addCard(Zone.BATTLEFIELD, playerA, "Laboratory Maniac", 1);
        // If you would draw a card, draw two cards instead. 
        addCard(Zone.BATTLEFIELD, playerA, "Thought Reflection", 4);


        setStopAt(40, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals("Player A library is empty", 0 , playerA.getLibrary().size());
        Assert.assertTrue("Player A has not won but should have", playerA.hasWon());
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
    /**
     * If I have resolved an Angel's Grace this turn, have an empty library, a Laboratory Maniac on 
     * the battlefield, and would draw a card, nothing happens. I should win the game if the card drawing effect resolves.
     */
    @Test
    public void testAngelsGrace() {
        addCard(Zone.HAND, playerA, "Angel's Grace");
        // Prevent the next 1 damage that would be dealt to target creature or player this turn.
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
        Assert.assertTrue("Player A has not won but should have", playerA.hasWon());
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
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Serum Visions");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Angel's Grace", 1);
        assertGraveyardCount(playerA, "Ad Nauseam", 1);
        assertGraveyardCount(playerA, "Serum Visions", 1);
        
        Assert.assertEquals("Player A library is empty", 0 , playerA.getLibrary().size());

        assertLife(playerA, -5);
        assertLife(playerB, 20);
        
        Assert.assertTrue("Player A has not won but should have", playerA.hasWon());

    }    
}
