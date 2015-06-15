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
package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ReturnToHandTest extends CardTestPlayerBase {

    /**
     * Tests to put a token onto the battlefield
     */
    @Test
    public void SkarrganFirebirdTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // Bloodthirst 3
        // Flying
        // {R}{R}{R}: Return Skarrgan Firebird from your graveyard to your hand. Activate this ability only if an opponent was dealt damage this turn.        
        addCard(Zone.BATTLEFIELD, playerB, "Skarrgan Firebird");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Bone Splinters");
        
        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerB, "Skarrgan Firebird");
        
        attack(2, playerB, "Silvercoat Lion");
        
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{R}{R}{R}: Return");
        setStopAt(2, PhaseStep.END_TURN);
        
        execute();

        assertPermanentCount(playerA, "Skarrgan Firebird", 0);
        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerB, "Bone Splinters", 1);
        assertHandCount(playerB, "Skarrgan Firebird", 1);

    }

    
}