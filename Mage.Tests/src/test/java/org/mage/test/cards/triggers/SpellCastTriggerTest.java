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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SpellCastTriggerTest extends CardTestPlayerBase {

    /**
     * Tests Sunscorch Regent
     */
    @Test
    public void testSunscorchRegent() {
        // Creature - Dragon 4/3
        // Flying
        // Whenever an opponent casts a spell, put a +1/+1 counter on Sunscorch Regent and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Sunscorch Regent", 1);

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);        

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18); // 20 -3 +1
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        
        assertPowerToughness(playerA, "Sunscorch Regent", 5, 4);
    }

    /**
     * Monastery Mentor triggers are causing a "rollback" error.
     */
    @Test
    public void testMonasteryMentor() {        
        // Prowess (Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.)
        // Whenever you cast a noncreature spell, put a 1/1 white Monk creature token with prowess onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Monastery Mentor", 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20); 
        assertLife(playerB, 14);
        
        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertPermanentCount(playerA, "Monk", 2);
        assertPowerToughness(playerA, "Monk", 2, 2);
        assertPowerToughness(playerA, "Monk", 1, 1);
        
        assertPowerToughness(playerA, "Monastery Mentor", 4, 4);
    }    
}
