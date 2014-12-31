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

import mage.abilities.Ability;
import mage.abilities.keyword.PoisonousAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 * @author dokkaebi
 */
public class PoisonousTest extends CardTestPlayerBase {

    public void assertMultipleInstancesOfAbility(Player player, String cardName, Ability ability, int count)
            throws AssertionError {
        int permanentCount = 0;
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent.getName().equals(cardName)) {
                found = permanent;
                permanentCount++;
            }
        }

        Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName() +
                ", cardName=" + cardName, found);

        Assert.assertTrue("There is more than one such permanent under player's control, player=" + player.getName() +
                ", cardName=" + cardName, permanentCount == 1);

        int abilityCount = 0;
        for (Ability existingAbility : found.getAbilities()) {
            if (existingAbility.getRule().equals(ability.getRule())) {
                abilityCount++;
            }
        }

        Assert.assertEquals("Ability count mismatch (" + ability.getRule() + "). Expected " + count + ", got " + abilityCount, count, abilityCount);
    }




    @Test
    public void testNormalCombatDamageIsDealt() {
        // Virulent Sliver gives all slivers Poisonous 1
        addCard(Zone.BATTLEFIELD, playerA, "Virulent Sliver", 1);

        attack(3, playerA, "Virulent Sliver");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }

    @Test
    public void testCombatDamageAddsPoisonCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Virulent Sliver", 1);

        attack(3, playerA, "Virulent Sliver");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 0);
        assertCounterCount(playerB, CounterType.POISON, 1);
    }

    @Test
    public void testMultipleInstancesAddMultipleCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Virulent Sliver", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Manaweft Sliver", 1);
        attack(3, playerA, "Manaweft Sliver");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertMultipleInstancesOfAbility(playerA, "Manaweft Sliver", new PoisonousAbility(1), 2);
        assertCounterCount(playerB, CounterType.POISON, 2);
        assertLife(playerB, 19);
    }

    @Test
    public void testNumberOfCountersNotTiedToDamageDealt() {
        addCard(Zone.BATTLEFIELD, playerA, "Virulent Sliver", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Battle Sliver", 2);

        attack(3, playerA, "Virulent Sliver");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount(playerB, CounterType.POISON, 1);
        assertLife(playerB, 15);
    }

    @Test
    public void testBlockedDamageDoesntAddPoisonCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Virulent Sliver", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Frost", 1);

        attack(3, playerA, "Virulent Sliver");
        block(3, playerB, "Wall of Frost", "Virulent Sliver");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount(playerB, CounterType.POISON, 0);
        assertLife(playerB, 20);
    }

    @Test
    public void testPreventedCombatDamageDoesntAddPoisonCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Virulent Sliver", 1);
        addCard(Zone.HAND, playerB, "Fog", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 10);

        attack(3, playerA, "Virulent Sliver");
        // reduce the attacker to negative power
        castSpell(3, PhaseStep.DECLARE_BLOCKERS, playerB, "Fog");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount(playerB, CounterType.POISON, 0);
        assertLife(playerB, 20);
    }

    @Test
    public void testZeroCombatDamageDoesntAddPoisonCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Virulent Sliver", 1);
        addCard(Zone.HAND, playerB, "Hydrosurge", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 10);

        attack(3, playerA, "Virulent Sliver");
        // reduce the attacker to negative power
        castSpell(3, PhaseStep.DECLARE_BLOCKERS, playerB, "Hydrosurge", "Virulent Sliver");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Virulent Sliver", -4, 1);
        assertCounterCount(playerB, CounterType.POISON, 0);
        assertLife(playerB, 20);
    }

}
