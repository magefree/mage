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
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
/**
 * with Melira, Sylvok Outcast on the table and Devoted Druid you can activated
 * his untap ability for infinity mana. This shouldn't work like this as its an
 * unpayable cost " 601.2g. The player pays the total cost in any order. Partial
 * payments are not allowed. Unpayable costs cant be paid"
 */
public class MeliraSylvokOutcastTest extends CardTestPlayerBase {

    /**
     * Test that the target of Vines of Vastwood can't be the target of spells
     * or abilities your opponents control this turn
     */
    @Test
    public void testUnpayableCost() {
        // You can't get poison counters.
        // Creatures you control can't have -1/-1 counters placed on them.
        // Creatures your opponents control lose infect.
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast", 2); // 2/2
        // {T}: Add {G} to your mana pool.
        // Put a -1/-1 counter on Devoted Druid: Untap Devoted Druid.
        addCard(Zone.BATTLEFIELD, playerA, "Devoted Druid", 1); // 0/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G} to your mana pool");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a -1/-1 counter on ");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Devoted Druid", 0, 2);
        assertCounterCount("Devoted Druid", CounterType.M1M1, 0);
        assertTapped("Devoted Druid", true); // Because untapping can't be paid

    }
}
