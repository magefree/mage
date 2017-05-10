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

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Check if attacking a planswalker and removing loyality counter from damage
 * works
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
     * Tests that giving a planeswalker hexproof does not prevent opponent from
     * attacking it
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

    /*
     * Reported bug: see issue #3328
     * Players unable to attack Planeswalker with Privileged Position on battlefield.
     */
    @Test
    public void testAttackPlaneswalkerWithHexproofPrivilegedPosition() {

        /*
         Privileged Position {2}{G/W}{G/W}{G/W}
        Enchantment
        Other permanents you control have hexproof.
         */
        String pPosition = "Privileged Position";
        String sorin = "Sorin, Solemn Visitor"; // planeswalker {2}{W}{B} 4 loyalty
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerB, pPosition);
        addCard(Zone.BATTLEFIELD, playerB, sorin);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(3, playerA, memnite, sorin);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, pPosition, 1);
        assertPermanentCount(playerB, sorin, 1);
        assertTapped(memnite, true);
        assertLife(playerB, 20);
        assertCounterCount(sorin, CounterType.LOYALTY, 3);
        assertAbility(playerB, sorin, HexproofAbility.getInstance(), true);
    }

    /**
     * Tests that attacking a planeswalker triggers and resolves Silent Skimmer
     * correctly
     */
    @Test
    public void testAttackPlaneswalkerTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, "Kiora, the Crashing Wave");

        // Devoid, Flying
        // Whenever Silent Skimmer attacks, defending player loses 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Silent Skimmer");

        attack(2, playerB, "Silent Skimmer", "Kiora, the Crashing Wave");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Kiora, the Crashing Wave", 1);
        assertPermanentCount(playerB, "Silent Skimmer", 1);
        assertCounterCount("Kiora, the Crashing Wave", CounterType.LOYALTY, 2);
    }
}
