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
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Dryad Militant
 * Creature - Dryad Soldier  1/1  {G/W}
 * If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
 * 
 * @author LevelX2
 */
public class DryadMilitantTest extends CardTestPlayerBase {
    
    /**
     * Tests that an instant or sorcery card is moved to exile
     */
    @Test
    public void testNormalCase() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerB, 17);
        assertExileCount("Lightning Bolt", 1);
    }    
    /**
     * Tests if Dryad Militant dies by damage spell, the
     * spell gets exiled
     */
    @Test
    public void testDiesByDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dryad Militant");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerB, 20);
        
        assertExileCount("Lightning Bolt", 1);
    }   
    
    /**
     * Tests if Dryad Militant dies by damage spell, the
     * spell don't get exiled
     */
    @Test
    public void testDiesByDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Terminate");
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terminate", "Dryad Militant");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerB, 20);

        assertHandCount(playerA, "Terminate", 0);
        assertGraveyardCount(playerB, "Terminate", 1);
    }       
}


