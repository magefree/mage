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
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DontUntapTest extends CardTestPlayerBase {

    /**
     * Test that the attackers blocked by creatures boosted with
     * Triton Tactics do not untap in their controllers next untap step
     */
    @Test
    public void testTritonTactics() {
        // Up to two target creatures each get +0/+3 until end of turn. Untap those creatures.
        // At this turn's next end of combat, tap each creature that was blocked by one of those
        // creatures this turn and it doesn't untap during its controller's next untap step.        
        addCard(Zone.HAND, playerA, "Triton Tactics");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // {T}: You gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        
        activateAbility(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: You gain 1 life");
        
        attack(4, playerB, "Silvercoat Lion");        
        castSpell(4, PhaseStep.DECLARE_ATTACKERS, playerA, "Triton Tactics", "Soulmender");
        block(4, playerA, "Soulmender", "Silvercoat Lion");
        
        setStopAt(6, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Triton Tactics", 1);

        assertPowerToughness(playerA, "Soulmender", 1, 1);
        
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertTapped("Silvercoat Lion", true); // Should be stilled tapped in turn 6 because it was blocked in turn 4 with Triton Tactics
        
        assertLife(playerA, 21);
        assertLife(playerB, 20);

    }
}