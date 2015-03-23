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

package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class VarolzTheScarStripedTest extends CardTestPlayerBase {

    /** 
     * Varolz, the Scar-Striped
     * Legendary Creature — Troll Warrior 2/2, 1BG (3)
     * Each creature card in your graveyard has scavenge. The scavenge cost is 
     * equal to its mana cost. (Exile a creature card from your graveyard and 
     * pay its mana cost: Put a number of +1/+1 counters equal to that card's 
     * power on target creature. Scavenge only as a sorcery.)
     * Sacrifice another creature: Regenerate Varolz, the Scar-Striped.
     *
     */

    @Test
    public void testUseScavenge() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Varolz, the Scar-Striped");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.GRAVEYARD, playerA, "Goblin Roughrider");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scavenge", "Goblin Roughrider");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertGraveyardCount(playerA, "Goblin Roughrider", 0);
        assertExileCount("Goblin Roughrider", 1);
        assertCounterCount("Goblin Roughrider", CounterType.P1P1, 3);

    }

}
