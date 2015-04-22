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

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ProvokeTest extends CardTestPlayerBase{
    
    @Test
    public void testProvokeTappedCreature() {
        // Creature - Beast 5/3        
        // Trample
        // Provoke (When this attacks, you may have target creature defending player controls untap and block it if able.)
        addCard(Zone.BATTLEFIELD, playerA, "Brontotherium");
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        attack(2, playerB, "Silvercoat Lion"); // So it's tapped
        
        attack(3, playerA, "Brontotherium"); 
        addTarget(playerA, "Silvercoat Lion");
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18); // one attack from Lion
        assertLife(playerB, 17); // Because of Trample
        
        assertPermanentCount(playerA, "Brontotherium", 1);
        assertGraveyardCount(playerB,"Silvercoat Lion", 1);
    }
    
    @Test
    public void testProvokeCreatureThatCantBlock() {
        // Creature - Beast 5/3        
        // Trample
        // Provoke (When this attacks, you may have target creature defending player controls untap and block it if able.)
        addCard(Zone.BATTLEFIELD, playerA, "Brontotherium");
        
        // Creature - Zombie Imp 1/1
        // Discard a card: Putrid Imp gains flying until end of turn.
        // Threshold - As long as seven or more cards are in your graveyard, Putrid Imp gets +1/+1 and can't block.
        addCard(Zone.BATTLEFIELD, playerB, "Putrid Imp", 1);
        addCard(Zone.GRAVEYARD, playerB, "Swamp", 7);
        
        attack(2, playerB, "Putrid Imp"); // So it's tapped
        
        attack(3, playerA, "Brontotherium"); 
        addTarget(playerA, "Putrid Imp");
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Brontotherium", 1);
        assertPermanentCount(playerB, "Putrid Imp", 1); // Can't Block so still alive

        assertLife(playerA, 18); // one attack from Imp
        assertLife(playerB, 15); // Not blocked
        
    }    
}
