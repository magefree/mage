/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/


package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Check if attacking a planswalker and removing loyality counter from damage works
 *
 * @author LevelX2
 */
public class AttackPlaneswalkerTest extends CardTestPlayerBase {

    @Test
    public void testAttackPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Kiora, the Crashing Wave");
        addCard(Zone.BATTLEFIELD, playerB, "Giant Tortoise");

        attack(2, playerB, "Giant Tortoise", "Kiora, the Crashing Wave");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Kiora, the Crashing Wave", 1);
        assertPermanentCount(playerB, "Giant Tortoise", 1);
        assertCounterCount("Kiora, the Crashing Wave", CounterType.LOYALTY, 1);
    }

    /**
     * Tests that giving a planeswalker hexproof does not prevent opponent from attacking it
     */
    @Test
    public void testAttackPlaneswalkerWithHexproof() {
        /*
         Simic Charm English
         Instant, UG
         Choose one — Target creature gets +3/+3 until end of turn;
                       or permanents you control gain hexproof until end of turn;
                       or return target creature to its owner's hand.
        */
        addCard(Zone.HAND, playerA, "Simic Charm");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Kiora, the Crashing Wave");

        addCard(Zone.BATTLEFIELD, playerB, "Giant Tortoise");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Simic Charm");
        setModeChoice(playerA, "2");

        attack(2, playerB, "Giant Tortoise", "Kiora, the Crashing Wave");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Kiora, the Crashing Wave", 1);
        assertPermanentCount(playerB, "Giant Tortoise", 1);
        assertCounterCount("Kiora, the Crashing Wave", CounterType.LOYALTY, 1);
    }

}
