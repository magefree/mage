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

public class MetalcraftTest extends CardTestPlayerBase {

    /**
     * Rusted Relic or Blinkmoth nexus is bugged	
     * Either Relic does not see Blinkmoth as an artifact or it does not turn 
     * into one when it should.
     * 
     */
    @Test
    public void testMetalcraftFromBlinkmoth() {
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Citadel",1); 

        // Metalcraft - {this} is a 5/5 Golem artifact creature as long as you control three or more artifacts
        addCard(Zone.BATTLEFIELD, playerA, "Rusted Relic", 1); 

        // {T}: Add {1}to your mana pool.
        // {1}: Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying until end of turn. It's still a land.
        // {1}, {T}: Target Blinkmoth creature gets +1/+1 until end of turn.        
        addCard(Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1); 
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: Until end of turn {this} becomes ");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Blinkmoth Nexus", 1, 1);
        assertPowerToughness(playerA, "Rusted Relic", 5, 5);
        
    }             
     
}
