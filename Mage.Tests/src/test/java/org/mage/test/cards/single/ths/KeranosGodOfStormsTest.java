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
package org.mage.test.cards.single.ths;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KeranosGodOfStormsTest extends CardTestPlayerBase {

    @Test
    public void testKeranosNormal() {
        // Reveal the first card you draw on each of your turns. 
        // Whenever you reveal a land card this way, draw a card. 
        // Whenever you reveal a nonland card this way, Keranos deals 3 damage to target creature or player.        
        addCard(Zone.BATTLEFIELD, playerB, "Keranos, God of Storms"); // {3}{U}{R}
        // Look at target player's hand.
        // Draw a card.
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 3);
        skipInitShuffling();

        addTarget(playerB, playerA); // damage from Keranos trigger

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Keranos, God of Storms", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1); // 1 Lion from the draw and 1 Lion from Peek
        
        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }
    
    /**
     * Keranos, God of Storms, will look at the first card drawn after he is
     * played, not the first card drawn each turn.
     *
     * My opponent, draws, plays Keranos, then plays Stroke of Genius. Keranos
     * triggers on the first card for Stroke of Genius.
     */
    @Test
    public void testKeranosCastAfterFirstDraw() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        // Reveal the first card you draw on each of your turns. 
        // Whenever you reveal a land card this way, draw a card. 
        // Whenever you reveal a nonland card this way, Keranos deals 3 damage to target creature or player.        
        addCard(Zone.HAND, playerB, "Keranos, God of Storms"); // {3}{U}{R}
        // Look at target player's hand.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Peek");
        
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 3);
        skipInitShuffling();

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Keranos, God of Storms");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Peek", playerA); // you won't do damage because it's not the first draw this turn - Draw in draw phase was the first
        addTarget(playerB, playerA); // not needed if it works correct

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Keranos, God of Storms", 1);
        assertGraveyardCount(playerB, "Peek", 1);

        assertHandCount(playerB, "Silvercoat Lion", 2); // 1 Lion from the draw and 1 Lion from Peek
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
