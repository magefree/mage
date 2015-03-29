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

package org.mage.test.cards.dynamicvalue;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SweepTest extends CardTestPlayerBase {


    /**
     * Plow Through Reito
     * 1W
     * Instant -- Arcane
     * Sweep -- Return any number of Plains you control to their owner's hand. 
     * Target creature gets +1/+1 until end of turn for each Plains returned this way.
     * 
    */
    @Test
    public void testSweep1x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");
        addTarget(playerA, "Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 4);
        assertPowerToughness(playerA,  "Raging Goblin", 2, 2);
        
    }
    
    @Test
    public void testSweep2x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");
        addTarget(playerA, "Plains^Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 3);
        assertPowerToughness(playerA,  "Raging Goblin", 3, 3);
        
    }

    @Test
    public void testSweep3x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");
        addTarget(playerA, "Plains^Plains^Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 2);
        assertPowerToughness(playerA,  "Raging Goblin", 4, 4);
        
    }

    @Test
    public void testSweep0x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 5);
        assertPowerToughness(playerA,  "Raging Goblin", 1, 1);
        
    }
    
}
