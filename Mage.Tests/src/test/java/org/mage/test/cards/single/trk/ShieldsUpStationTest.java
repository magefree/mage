package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Shields Up! (TRK): "Target artifact or creature you control gains hexproof and
 * indestructible until end of turn. If it's a creature, put a +1/+1 counter on it."
 *
 * CR 721.2b: a station permanent with N or more charge counters "is a creature
 * with base power and toughness [P/T] in addition to its other types", so a
 * Spacecraft with enough charge counters is a creature on resolution and gets the counter.
 * CR 608.2c: the controller follows the instructions in order; "if it's a creature"
 * is read from the permanent as it is now, layer effects included.
 */
public class ShieldsUpStationTest extends CardTestPlayerBase {

    @Test
    public void testStationedSpacecraftGetsCounter() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Romulan Bird-of-Prey");
        addCounters(1, PhaseStep.UPKEEP, playerA, "Romulan Bird-of-Prey", CounterType.CHARGE, 8);
        addCard(Zone.HAND, playerA, "Shields Up!");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shields Up!", "Romulan Bird-of-Prey");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Romulan Bird-of-Prey", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Romulan Bird-of-Prey", 5, 5);
    }

    @Test
    public void testUnstationedSpacecraftGetsNoCounter() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Romulan Bird-of-Prey");
        addCard(Zone.HAND, playerA, "Shields Up!");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shields Up!", "Romulan Bird-of-Prey");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Romulan Bird-of-Prey", CounterType.P1P1, 0);
    }
}
