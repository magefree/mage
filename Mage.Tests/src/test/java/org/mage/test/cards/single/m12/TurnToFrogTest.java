package org.mage.test.cards.single.m12;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests triggered abilities that are added to permanents
 * 
 * @author BetaSteward
 */
public class TurnToFrogTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {2}{R}{G}: Until end of turn, Raging Ravine becomes a 3/3 red and green Elemental creature with "Whenever this creature attacks, put a +1/+1 counter on it." It's still a land.
        addCard(Zone.BATTLEFIELD, playerA, "Raging Ravine");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Until end of turn, target creature loses all abilities and becomes a blue Frog with base power and toughness 1/1.
        addCard(Zone.HAND, playerB, "Turn to Frog");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}{G}: Until end of turn, {this} becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" It's still a land.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Frog", "Raging Ravine");
        attack(1, playerA, "Raging Ravine");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPowerToughness(playerA, "Raging Ravine", 1, 1, Filter.ComparisonScope.Any);
        assertCounterCount("Raging Ravine", CounterType.P1P1, 0);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Ravine");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Turn to Frog");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}{G}: Until end of turn, {this} becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" It's still a land.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Frog", "Raging Ravine");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}{G}: Until end of turn, {this} becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" It's still a land.");
        attack(3, playerA, "Raging Ravine");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
        assertPowerToughness(playerA, "Raging Ravine", 4, 4, Filter.ComparisonScope.Any);
        assertCounterCount("Raging Ravine", CounterType.P1P1, 1);
    }

    @Test
    public void testCard3() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Public Execution");
        // Turn to Frog - Instant, 1U - Target creature loses all abilities and becomes a 1/1 blue Frog until end of turn.
        addCard(Zone.HAND, playerA, "Turn to Frog");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        // Craw Wurm - Creature — Wurm 6/4, 4GG
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Public Execution", "Llanowar Elves");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Turn to Frog", "Craw Wurm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Llanowar Elves", 0);
        assertPowerToughness(playerB, "Craw Wurm", -1, 1);
    }
}
