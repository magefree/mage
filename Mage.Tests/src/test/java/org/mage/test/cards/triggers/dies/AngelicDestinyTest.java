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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 
 * 
 * @author LevelX2
 */

public class AngelicDestinyTest extends CardTestPlayerBase {

    /**
     * I killed my opponent's Champion of the Parish, which was enchanted with Angelic Destiny. 
     * However the Angelic Destiny went to the graveyard instead of returning his hand.
     * 
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Enchantment - Aura    {2}{W}{W}
        // Enchant creature
        // Enchanted creature gets +4/+4, has flying and first strike, and is an Angel in addition to its other types.
        // When enchanted creature dies, return Angelic Destiny to its owner's hand.
        addCard(Zone.HAND, playerA, "Angelic Destiny", 1);
        // Champion of the Parish
        // Whenever another Human enters the battlefield under your control, put a +1/+1 counter on Champion of the Parish.
        addCard(Zone.BATTLEFIELD, playerA, "Champion of the Parish", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Terminate", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Destiny", "Champion of the Parish");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Terminate", "Champion of the Parish");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertGraveyardCount(playerA, "Champion of the Parish", 1);
        assertGraveyardCount(playerB, "Terminate", 1);
        
        assertGraveyardCount(playerA, "Angelic Destiny", 0);
        assertHandCount(playerA, "Angelic Destiny", 1);
        
    }

}