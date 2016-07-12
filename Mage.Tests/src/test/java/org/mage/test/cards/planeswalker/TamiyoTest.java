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
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class TamiyoTest extends CardTestPlayerBase {

    /*
     * Reported bug: I activated Tamiyo's +1 ability on a 5/5 Gideon and his 2/2 Knight Ally, but when they both attacked 
     *  and dealt damage I only drew one card when I'm pretty sure I was supposed to draw for each of the two.
    
     * NOTE: cannot determine how to have the test recognize the 2/2 knight ally token from Gideon
     * added ignore flag until then
     */
    @Ignore
    @Test
    public void testFieldResearcherFirstEffect() {
        
        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
        // +1: Choose up to two target creatures. Until your next turn, 
        // whenever either of those creatures deals combat damage, you draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Tamiyo, Field Researcher", 1);
        
        /* Gideon, Ally of Zendikar {2}{W}{W} - 4 loyalty
         * +1: Until end of turn, Gideon, Ally of Zendikar becomes a 5/5 Human Soldier Ally creature with indestructible 
         * that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
         * 0: Put a 2/2 white Knight Ally creature token onto the battlefield.
        **/
        addCard(Zone.BATTLEFIELD, playerA, "Gideon, Ally of Zendikar", 1);
        
        // put 2/2 knight ally token on battlefield
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Put a");
        
        // next, activate Gideon to make him a 5/5 human soldier ally creature
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until end of turn");
        // finally, use Tamiyo +1 on both creatures
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Knight Ally"); // 2/2 token
        addTarget(playerA, "Gideon, Ally of Zendikar"); // 5/5 human soldier ally
        
        // attack with both unblocked
        attack(3, playerA, "Knight Ally"); 
        attack(3, playerA, "Gideon, Ally of Zendikar");
        
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();        
        
        assertLife(playerB, 13); // 5 + 2 damage, 20 - 7 = 13
        assertPermanentCount(playerA, "Tamiyo, Field Researcher", 1);
        assertPermanentCount(playerA, "Gideon, Ally of Zendikar", 1);
        assertPermanentCount(playerA, "Knight Ally", 1);
        assertHandCount(playerA, 3); // two cards drawn from each creature dealing damage + 1 card drawn on turn
    }
}
