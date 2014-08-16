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
 * 702.90. Battle Cry
 *
 *  702.90a Battle cry is a triggered ability. "Battle cry" means "Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn."
 *  702.90b If a creature has multiple instances of battle cry, each triggers separately.
 *
 * @author LevelX2
 */

public class BattleCryTest extends CardTestPlayerBase {

    /**
     * Tests boost last until end of turn
     */
    @Test
    public void testBoostDurationUntilEndTurn() {
        // Signal Pest {1}
        // Artifact Creature - Pest   0/1
        // Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)
        // Signal Pest can't be blocked except by creatures with flying or reach.
        addCard(Zone.BATTLEFIELD, playerB, "Signal Pest", 3);

        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Signal Pest", 3);
        assertPowerToughness(playerB, "Signal Pest", 2,1); // two other Signal Pest atack, so it get +2 power
    }

    /**
     * Tests boost last until end of turn
     */
    @Test
    public void testBoostDurationNotNextTurn() {
        // Signal Pest {1}
        // Artifact Creature - Pest   0/1
        // Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)
        // Signal Pest can't be blocked except by creatures with flying or reach.
        addCard(Zone.BATTLEFIELD, playerB, "Signal Pest", 3);

        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Signal Pest", 3);
        assertPowerToughness(playerB, "Signal Pest", 0,1); 
    }

}
