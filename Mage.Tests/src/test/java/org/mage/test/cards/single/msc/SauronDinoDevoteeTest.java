package org.mage.test.cards.single.msc;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class SauronDinoDevoteeTest extends CardTestPlayerBase {

    // Flying
    // Whenever Sauron enters or attacks, choose one --
    // * Cure Cancer -- You gain 3 life.
    // * Turn People into Dinosaurs -- Put a saurian counter on another target creature. It's a green Dinosaur
    //   with base power and toughness 5/5 for as long as it has a saurian counter on it.
    private static final String sauron = "Sauron, Dino Devotee"; // 4/4
    private static final String ornithopter = "Ornithopter"; // 0/2 colorless Thopter artifact creature with flying
    private static final String hexmage = "Vampire Hexmage"; // Sacrifice: Remove all counters from target permanent.

    @Test
    public void test_EntersGainLife() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sauron);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sauron);
        setModeChoice(playerA, "1");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sauron, 1);
        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_AttacksSaurianCounter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sauron);
        addCard(Zone.BATTLEFIELD, playerA, ornithopter);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, ornithopter, CounterType.P1P1, 1);

        attack(1, playerA, sauron);
        setModeChoice(playerA, "2");
        addTarget(playerA, ornithopter);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 4);
        assertCounterCount(ornithopter, CounterType.SAURIAN, 1);
        assertColor(playerA, ornithopter, "G", true);
        assertSubtype(ornithopter, SubType.DINOSAUR);
        assertNotSubtype(ornithopter, SubType.THOPTER);
        assertType(ornithopter, CardType.ARTIFACT, true);
        assertAbility(playerA, ornithopter, FlyingAbility.getInstance(), true);
        // base 5/5 plus the +1/+1 counter
        assertPowerToughness(playerA, ornithopter, 5 + 1, 5 + 1);
    }

    @Test
    public void test_CounterRemoved() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sauron);
        addCard(Zone.BATTLEFIELD, playerA, ornithopter);
        addCard(Zone.BATTLEFIELD, playerA, hexmage);

        attack(1, playerA, sauron);
        setModeChoice(playerA, "2");
        addTarget(playerA, ornithopter);

        checkPT("dinosaur", 1, PhaseStep.END_COMBAT, playerA, ornithopter, 5, 5);
        checkColor("dinosaur", 1, PhaseStep.END_COMBAT, playerA, ornithopter, "G", true);
        checkSubType("dinosaur", 1, PhaseStep.END_COMBAT, playerA, ornithopter, SubType.DINOSAUR, true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice", ornithopter);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        // the effect has ended, a new saurian counter doesn't bring it back
        addCounters(1, PhaseStep.END_TURN, playerA, ornithopter, CounterType.SAURIAN, 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertCounterCount(ornithopter, CounterType.SAURIAN, 1);
        assertColor(playerA, ornithopter, "G", false);
        assertSubtype(ornithopter, SubType.THOPTER);
        assertNotSubtype(ornithopter, SubType.DINOSAUR);
        assertPowerToughness(playerA, ornithopter, 0, 2);
    }

    @Test
    public void test_CantTargetItself() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sauron);

        // Sauron is the only creature, so the second mode has no legal target
        attack(1, playerA, sauron);
        setModeChoice(playerA, "2");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertCounterCount(sauron, CounterType.SAURIAN, 0);
        assertSubtype(sauron, SubType.VAMPIRE);
        assertPowerToughness(playerA, sauron, 4, 4);
    }
}
