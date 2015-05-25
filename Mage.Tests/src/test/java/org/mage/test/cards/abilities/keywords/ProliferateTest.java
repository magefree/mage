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
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ProliferateTest extends CardTestPlayerBase{

    /**
     *  Volt Charge   {2}{R}
     *   Instant
     *   Volt Charge deals 3 damage to target creature or player.
     *  Proliferate. (You choose any number of permanents and/or players with counters
     *  on them, then give each another counter of a kind already there.)
     */
    @Test
    public void testCastFromHandMovedToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Pyromaster");  // starts with 4 loyality counters

        addCard(Zone.HAND, playerA, "Volt Charge");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volt Charge", playerB);
        addTarget(playerA, "Chandra, Pyromaster");


        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Volt Charge", 1);

        assertCounterCount("Chandra, Pyromaster", CounterType.LOYALTY, 5); // 4 + 1 from proliferate

    }
    
    /**
     * Counters aren't cancelling each other out. Reproducible with any creature (graft and bloodthirst in my case)
     * with a single +1/+1 counter on it, with a single -1/-1 placed on it (Grim Affliction, Instill Infection, etc). 
     * The counters should cancel each other out, leaving neither on the creature, which they don't (though visually
     * there aren't any counters sitting on the card). Triggering proliferate at any point now (Thrumming Bird, 
     * Steady Progress, etc) will give you the option to add another of either counter, where you shouldn't have any as an option.
     */
    @Test
    public void testValidTargets() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);        
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");  
        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Battlegrowth"); // {G}
        // Proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        // Draw a card.
        addCard(Zone.HAND, playerA, "Steady Progress"); // {U}{2}

        addCard(Zone.BATTLEFIELD, playerB, "Sporeback Troll");  // has two +1/+1 counter
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        // Put a -1/-1 counter on target creature, then proliferate.        
        addCard(Zone.HAND, playerB, "Grim Affliction"); // {B}{2}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Silvercoat Lion");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grim Affliction", "Silvercoat Lion");
        // proliferate Sporeback Troll
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Steady Progress");
        // Silvercoat Lion may not be a valid target now
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();


        assertGraveyardCount(playerA, "Battlegrowth", 1);
        assertGraveyardCount(playerA, "Steady Progress", 1);
        assertGraveyardCount(playerB, "Grim Affliction", 1);

        assertCounterCount("Silvercoat Lion", CounterType.P1P1, 0); // no valid target because no counter
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 3); // 2 + 1 from proliferate

    }    
}
