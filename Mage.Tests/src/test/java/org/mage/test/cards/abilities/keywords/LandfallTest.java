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
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.IntimidateAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class LandfallTest extends CardTestPlayerBase {

    @Test
    public void testNormalUse() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains",3); 
        addCard(Zone.HAND, playerA, "Plains"); 

        // Instant - {1}{W}
        // Target player gains 4 life.
        // Landfall - If you had a land enter the battlefield under your control this turn, that player gains 8 life instead.        
        addCard(Zone.HAND, playerA, "Rest for the Weary",2); 
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rest for the Weary");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Rest for the Weary");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 4);
        assertGraveyardCount(playerA, "Rest for the Weary", 2);
        assertLife(playerA, 32); // + 8 from 1 turn + 4 from second turn
        assertLife(playerB, 20);        
        
    }     
    /**
     * If you Hive Mind an opponent's Rest for the Weary and redirect its target to yourself when it's not your turn, 
     * the game spits out this message and rolls back to before Rest for the Weary was cast.
     * 
     */
    @Test
    public void testHiveMind() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains",2); 
        
        // Whenever a player casts an instant or sorcery spell, each other player copies that spell. Each of those players may choose new targets for his or her copy.
        addCard(Zone.BATTLEFIELD, playerB, "Hive Mind"); 

        // Instant - {1}{W}
        // Target player gains 4 life.
        // Landfall - If you had a land enter the battlefield under your control this turn, that player gains 8 life instead.        
        addCard(Zone.HAND, playerA, "Rest for the Weary",1); 
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rest for the Weary");        

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rest for the Weary", 1);
        assertLife(playerA, 24); 
        assertLife(playerB, 24);        
        
    }   
    
    @Test
    public void testSurrakarMarauder() {
        // Landfall - Whenever a land enters the battlefield under your control, Surrakar Marauder gains intimidate until end of turn.         
        addCard(Zone.BATTLEFIELD, playerA, "Surrakar Marauder",1); 
        addCard(Zone.HAND, playerA, "Plains"); 
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        
        assertAbility(playerA, "Surrakar Marauder", IntimidateAbility.getInstance(), true);
        
        assertLife(playerA, 20); 
        assertLife(playerB, 20);        
        
    }        
    
    /**
     * Searing Blaze's landfall doesn't appear to be working. My opponent played
     * a mountain, then played searing blaze targeting my Tasigur, the Golden
     * Fang. It only dealt 1 damage to me, where it should've dealt 3, because
     * my opponent had played a land.
     */
    @Test
    public void testSearingBlaze() {
        // Searing Blaze deals 1 damage to target player and 1 damage to target creature that player controls.
        // Landfall - If you had a land enter the battlefield under your control this turn, Searing Blaze deals 3 damage to that player and 3 damage to that creature instead.
        addCard(Zone.HAND, playerA, "Searing Blaze",1); 
        addCard(Zone.BATTLEFIELD, playerA, "Mountain",1); 
        addCard(Zone.HAND, playerA, "Mountain"); 

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion",1); 

                
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Blaze"); 
        
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mountain", 2);
        assertGraveyardCount(playerA, "Searing Blaze" , 1);
                
        assertLife(playerA, 20); 
        assertLife(playerB, 17);        
        
        assertGraveyardCount(playerB, "Silvercoat Lion" , 1);
        
    }        
     
}
