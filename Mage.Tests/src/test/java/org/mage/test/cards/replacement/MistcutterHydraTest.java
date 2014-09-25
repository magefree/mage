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
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MistcutterHydraTest extends CardTestPlayerBase {

    /**
     * Test that a Mistcutter Hydra exiled with Banishing Light returns with 0 Counters on it
     */
    @Test
    public void testHydraReturnsWithZeroCounters() {
        addCard(Zone.HAND, playerA, "Banishing Light");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        // Mistcutter Hydra
        // Creature — Hydra 0/0, XG (1)
        // Mistcutter Hydra can't be countered.
        // Haste, protection from blue
        // Mistcutter Hydra enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerB, "Mistcutter Hydra");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerB, "Disenchant");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mistcutter Hydra");
        setChoice(playerB, "X=3");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Banishing Light");
        setChoice(playerA, "Mistcutter Hydra");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant","Banishing Light");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Banishing Light", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);
        assertGraveyardCount(playerB, "Mistcutter Hydra", 1);
        assertPermanentCount(playerB, "Mistcutter Hydra", 0);
    }

}
