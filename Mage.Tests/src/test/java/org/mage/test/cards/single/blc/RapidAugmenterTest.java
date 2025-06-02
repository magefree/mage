package org.mage.test.cards.single.blc;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class RapidAugmenterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.r.RapidAugmenter Rapid Augmenter} {1}{U}{R}
     * Creature — Otter Artificer
     * Haste
     * Whenever another creature you control with base power 1 enters, it gains haste until end of turn.
     * Whenever another creature you control enters, if it wasn’t cast, put a +1/+1 counter on Rapid Augmenter and
     * Rapid Augmenter can’t be blocked this turn.
     */
    private static final String rapidAugmenter = "Rapid Augmenter";

    @Test
    public void test_Cast21() {
        // Tests that casting a 2/1 does not trigger Rapid Augmenter
        addCard(Zone.BATTLEFIELD, playerA, rapidAugmenter);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Air Marshal", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Alpine Watchdog", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Air Marshal", true);

        attack(1, playerA, rapidAugmenter, playerB);
        // Rapid Augmenter can still be blocked
        block(1, playerB, "Alpine Watchdog", rapidAugmenter);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // First ability did not trigger
        assertAbility(playerA, "Air Marshal", HasteAbility.getInstance(), false);

        // Second ability did not trigger
        assertPowerToughness(playerA, rapidAugmenter, 1, 3);
        assertCounterCount(playerA, rapidAugmenter, CounterType.P1P1, 0);

        // Rapid Augmenter was blocked
        assertLife(playerB, currentGame.getStartingLife());

    }

    @Test
    public void test_Cast11() {
        // Tests that casting a 1/1 triggers Rapid Augmenter
        addCard(Zone.BATTLEFIELD, playerA, rapidAugmenter);
        addCard(Zone.HAND, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Alpine Watchdog", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", true);

        attack(1, playerA, rapidAugmenter, playerB);
        // 1/1 has haste so this will work
        attack(1, playerA, "Memnite", playerB);
        // Rapid Augmenter can still be blocked
        block(1, playerB, "Alpine Watchdog", rapidAugmenter);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // First ability triggered
        assertAbility(playerA, "Memnite", HasteAbility.getInstance(), true);

        // Second ability did not trigger
        assertPowerToughness(playerA, rapidAugmenter, 1, 3);
        assertCounterCount(playerA, rapidAugmenter, CounterType.P1P1, 0);

        // Rapid Augmenter was blocked, Memnite was not
        assertLife(playerB, currentGame.getStartingLife() - 1);

    }

    @Test
    public void test_Bounce21() {
        // Tests that bouncing a 2/1 triggers Rapid Augmenter's second ability, but not the first
        addCard(Zone.BATTLEFIELD, playerA, rapidAugmenter);
        addCard(Zone.BATTLEFIELD, playerA, "Air Marshal", 1);
        addCard(Zone.HAND, playerA, "Ephemerate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Alpine Watchdog", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", true);
        addTarget(playerA, "Air Marshal");

        attack(1, playerA, rapidAugmenter, playerB);
        // Rapid Augmenter can't be blocked, Alpine Watchdog wont take damage
        block(1, playerB, "Alpine Watchdog", rapidAugmenter);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // First ability did not trigger
        assertAbility(playerA, "Air Marshal", HasteAbility.getInstance(), false);

        // Second ability triggered
        assertPowerToughness(playerA, rapidAugmenter, 1 + 1, 3 + 1);
        assertCounterCount(playerA, rapidAugmenter, CounterType.P1P1, 1);

        assertPermanentCount(playerB, "Alpine Watchdog", 1);
        assertLife(playerB, currentGame.getStartingLife() - 2);

    }

    @Test
    public void test_Bounce11() {
        // Tests that bouncing a 2/1 triggers both of Rapid Augmenter's abilities
        addCard(Zone.BATTLEFIELD, playerA, rapidAugmenter);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.HAND, playerA, "Ephemerate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Alpine Watchdog", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", true);
        addTarget(playerA, "Memnite");
        setChoice(playerA, "Whenever another creature you control enters"); // order triggers (doesnt matter the order but a choice must be made)

        attack(1, playerA, rapidAugmenter, playerB);
        // Rapid Augmenter can't be blocked, Alpine Watchdog wont take damage
        block(1, playerB, "Alpine Watchdog", rapidAugmenter);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // First ability triggered
        assertAbility(playerA, "Memnite", HasteAbility.getInstance(), true);

        // Second ability triggered
        assertPowerToughness(playerA, rapidAugmenter, 1 + 1, 3 + 1);
        assertCounterCount(playerA, rapidAugmenter, CounterType.P1P1, 1);

        assertPermanentCount(playerB, "Alpine Watchdog", 1);
        assertLife(playerB, currentGame.getStartingLife() - 2);

    }

}
