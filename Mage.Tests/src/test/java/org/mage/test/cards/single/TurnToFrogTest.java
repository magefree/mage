package org.mage.test.cards.single;

import mage.Constants;
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
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Raging Ravine");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Constants.Zone.HAND, playerB, "Turn to Frog");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}{G}: until end of turn {this} becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" that's still a land. ");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Frog", "Raging Ravine");
        attack(1, playerA, "Raging Ravine");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPowerToughness(playerA, "Raging Ravine", 1, 1, Filter.ComparisonScope.Any);
        assertCounterCount("Raging Ravine", CounterType.P1P1, 0);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Raging Ravine");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Constants.Zone.HAND, playerB, "Turn to Frog");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}{G}: until end of turn {this} becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" that's still a land. ");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Frog", "Raging Ravine");

        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}{G}: until end of turn {this} becomes a 3/3 red and green Elemental creature with \"Whenever this creature attacks, put a +1/+1 counter on it.\" that's still a land. ");
        attack(3, playerA, "Raging Ravine");

        setStopAt(3, Constants.PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
        assertPowerToughness(playerA, "Raging Ravine", 4, 4, Filter.ComparisonScope.Any);
        assertCounterCount("Raging Ravine", CounterType.P1P1, 1);
    }

    @Test
    public void testCard3() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Constants.Zone.HAND, playerA, "Public Execution");
        // Turn to Frog - Instant, 1U - Target creature loses all abilities and becomes a 1/1 blue Frog until end of turn.
        addCard(Constants.Zone.HAND, playerA, "Turn to Frog");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        // Craw Wurm - Creature — Wurm 6/4, 4GG
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Public Execution", "Llanowar Elves");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Turn to Frog", "Craw Wurm");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Llanowar Elves", 0);
        assertPowerToughness(playerB, "Craw Wurm", -1, 1);
    }

}
