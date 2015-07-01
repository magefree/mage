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
package org.mage.test.cards.requirement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AttackRequirementTest extends CardTestPlayerBase {


    @Test
    public void testSimpleAttackRequirement() {
        // Defender
        // {G}: Wall of Tanglecord gains reach until end of turn. (It can block creatures with flying.)
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Tanglecord"); // 0/6
        
        // Juggernaut attacks each turn if able.
        // Juggernaut can't be blocked by Walls
        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut"); // 5/3
        
        // Juggernaut should be forced to ttack 
        block(2, playerA, "Wall of Tanglecord", "Juggernaut"); // this block should'nt work because of Juggernauts restriction

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 15); 
        assertLife(playerB, 20);

    }


    @Test
    public void testAttackRequirementWithAttackRestriction() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Defender
        // {G}: Wall of Tanglecord gains reach until end of turn. (It can block creatures with flying.)
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Tanglecord"); // 0/6
        
        // Creatures can't attack you unless their controller pays {2} for each creature he or she controls that's attacking you
        addCard(Zone.HAND, playerA, "Ghostly Prison");
        
        // Juggernaut attacks each turn if able.
        // Juggernaut can't be blocked by Walls
        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut"); // 5/3
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ghostly Prison");
        
        // Juggernaut is forced to attack but can't without paying the Ghostly Prison cost and don't has to pay the costs so no attack

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20); 
        assertLife(playerB, 20);

    }
    
}
