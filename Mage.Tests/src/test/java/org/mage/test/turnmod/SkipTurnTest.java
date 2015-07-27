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
package org.mage.test.turnmod;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SkipTurnTest extends CardTestPlayerBase {

    @Test
    public void testEaterOfDays() {
        // At the beginning of your upkeep or whenever you cast a green spell, put a charge counter on Shrine of Boundless Growth.
        // {T}, Sacrifice Shrine of Boundless Growth: Add {1} to your mana pool for each charge counter on Shrine of Boundless Growth.
        addCard(Zone.HAND, playerA, "Shrine of Boundless Growth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // Flying
        // Trample
        // When Eater of Days enters the battlefield, you skip your next two turns.
        addCard(Zone.HAND, playerA, "Eater of Days", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eater of Days");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shrine of Boundless Growth");

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Eater of Days", 1);
        assertPermanentCount(playerA, "Shrine of Boundless Growth", 1);
        assertCounterCount("Shrine of Boundless Growth", CounterType.CHARGE, 1);
    }
}
