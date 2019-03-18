
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
