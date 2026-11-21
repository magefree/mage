package org.mage.test.cards.planes;

import mage.constants.PhaseStep;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FeedingGroundsTest extends CardTestPlayerBase {

    @Test
    public void test_ChaosPutsCountersOnCreatureEqualToItsManaValue() {
        // Whenever chaos ensues, put X +1/+1 counters on target creature, where X is that creature's mana value.
        addPlane(playerA, Planes.PLANE_FEEDING_GROUNDS);

        addCard(Zone.BATTLEFIELD, playerB, "Water Elemental"); // {3}{U}{U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // chaos
        addTarget(playerA, "Water Elemental"); // target a blue creature an opponent controls

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Water Elemental", CounterType.P1P1, 0 + 5);
    }

    @Test
    public void test_ChaosUsesTheManaValueOfTheTokenCopy() {
        // Whenever chaos ensues, put X +1/+1 counters on target creature, where X is that creature's mana value.
        addPlane(playerA, Planes.PLANE_FEEDING_GROUNDS);

        // Create a token that's a copy of target creature. If this spell was kicked, create five of those tokens instead.
        addCard(Zone.HAND, playerA, "Rite of Replication"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Water Elemental"); // {3}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of Replication", "Water Elemental");
        setChoice(playerA, false); // cast without paying kicker
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // chaos
        addTarget(playerA, "Water Elemental[only copy]"); // target the token copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Water Elemental", CounterType.P1P1, 0 + 5);
    }

    @Test
    public void test_ChaosPutsNoCountersOnAZeroManaValueCreature() {
        // Whenever chaos ensues, put X +1/+1 counters on target creature, where X is that creature's mana value.
        addPlane(playerA, Planes.PLANE_FEEDING_GROUNDS);

        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor"); // no mana cost

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // chaos
        addTarget(playerA, "Dryad Arbor"); // target a creature with mana value 0

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Dryad Arbor", CounterType.P1P1, 0);
    }
}
