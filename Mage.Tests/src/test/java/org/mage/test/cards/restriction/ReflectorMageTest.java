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
package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */

public class ReflectorMageTest extends CardTestPlayerBase {

    
    /**
     * Reported bug: Reflector Mage returning a creature to its owners hand is additionally
     * incorrectly preventing the Reflector Mage's owner from casting that same creature.
     */
    @Test
    public void testReflectorMageAllowsOwnerToCastCreatureReturnedOnSameTurn() {
        
        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand. 
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerA, "Reflector Mage"); // 2/3   
        addCard(Zone.HAND, playerA, "Bronze Sable", 1); // (2) 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); 
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);  
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reflector Mage");
        addTarget(playerA, "Bronze Sable");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bronze Sable");
        
        execute();
        
        assertPermanentCount(playerB, "Bronze Sable", 0);
        assertHandCount(playerB, "Bronze Sable", 1);    
        assertPermanentCount(playerA, "Reflector Mage", 1);
        assertPermanentCount(playerA, "Bronze Sable", 1);
    }
    
    /**
     * Basic test to confirm the restriction effect still works on the opponent.
     */
    @Test
    public void testReflectorMageRestrictionEffect() {
        
        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand. 
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerA, "Reflector Mage"); // 2/3   
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); 
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);  
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1); // (2) 2/1
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reflector Mage");
        addTarget(playerA, "Bronze Sable");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bronze Sable"); // should not be allowed
        execute();
        
        assertPermanentCount(playerB, "Bronze Sable", 0);
        assertHandCount(playerB, "Bronze Sable", 1);
        assertPermanentCount(playerA, "Reflector Mage", 1);
    }
}
