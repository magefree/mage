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
package org.mage.test.cards.single.ths;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class OrdealEnchantmentsTest extends CardTestPlayerBase {

    @Test
    public void testOrdealofHeliod() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Enchant creature
        // Whenever enchanted creature attacks, put a +1/+1 counter on it. Then if it has three or more +1/+1 counters on it, sacrifice Ordeal of Heliod.
        // When you sacrifice Ordeal of Heliod, you gain 10 life.        
        addCard(Zone.HAND, playerB, "Ordeal of Heliod");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);


        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ordeal of Heliod", "Silvercoat Lion");
        
        attack(2, playerB, "Silvercoat Lion");
        attack(4, playerB, "Silvercoat Lion");
        attack(6, playerB, "Silvercoat Lion");

        setStopAt(6, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 8); // 3 + 4 + 5 = 12
        assertLife(playerB, 30);
        
        assertGraveyardCount(playerB, "Ordeal of Heliod", 1);
        
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 5,5);
    }

 
}