package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SorinOfHouseMarkovTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SorinOfHouseMarkov Sorin of House Markov} {1}{B}
     * Legendary Creature — Human Noble
     * Lifelink
     * Extort (Whenever you cast a spell, you may pay {W/B}. If you do, each opponent loses 1 life and you gain that much life.)
     * At the beginning of your postcombat main phase, if you gained 3 or more life this turn, exile Sorin of House Markov, then him to the battlefield transformed under his owner’s control.
     * 1/4
     * // {@link mage.cards.s.SorinRavenousNeonate Sorin, Ravenous Neonate}
     * Legendary Planeswalker — Sorin
     * Extort (Whenever you cast a spell, you may pay {W/B}. If you do, each opponent loses 1 life and you gain that much life.)
     * +2: Create a Food token.
     * −1: Sorin, Ravenous Neonate deals damage equal to the amount of life you gained this turn to any target.
     * −6: Gain control of target creature. It becomes a Vampire in addition to its other types. Put a lifelink counter on it if you control a white permanent other than that creature or Sorin.
     * Loyalty: 3
     */
    private static final String sorin = "Sorin of House Markov";
    private static final String sorinPW = "Sorin, Ravenous Neonate";

    @Test
    public void test_Gain2Life_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sorin);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Courier Griffin"); // {3}{W} etb, gain 2 life

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Courier Griffin");
        setChoice(playerA, false); // no to Extort trigger

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Courier Griffin", 1);
        assertPermanentCount(playerA, sorin, 1);
        assertLife(playerA, 20 + 2);
    }

    @Test
    public void test_Gain3Life_Trigger_ThenMinus1() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sorin);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Courier Griffin"); // {3}{W} etb, gain 2 life

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Courier Griffin");
        setChoice(playerA, true); // pay for Extort

        checkPermanentCount("sorin did not transform yet", 1, PhaseStep.BEGIN_COMBAT, playerA, sorin, 1);

        // Sorin triggers post combat
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-1", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Courier Griffin", 1);
        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 1 - 3);
        assertPermanentCount(playerA, sorinPW, 1);
        assertCounterCount(playerA, sorinPW, CounterType.LOYALTY, 3 - 1);
    }

    @Test
    public void test_Plus2_Plus2_Minus1() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sorinPW);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+2");

        // Activate both foods
        activateAbility(5, PhaseStep.UPKEEP, playerA, "{2}, {T}, Sacrifice this artifact: You gain 3 life");
        activateAbility(5, PhaseStep.UPKEEP, playerA, "{2}, {T}, Sacrifice this artifact: You gain 3 life");

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "-1");
        addTarget(playerA, playerB);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 6);
        assertLife(playerB, 20 - 6);
        assertPermanentCount(playerA, sorinPW, 1);
        assertCounterCount(playerA, sorinPW, CounterType.LOYALTY, 3 + 2 + 2 - 1);
    }

    @Test
    public void test_Minus6_NoOtherWhite() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sorinPW);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // notably not white
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, sorinPW, CounterType.LOYALTY, 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-6");
        addTarget(playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertSubtype("Elite Vanguard", SubType.HUMAN);
        assertSubtype("Elite Vanguard", SubType.VAMPIRE); // Vampire in addition
        assertCounterCount(playerA, "Elite Vanguard", CounterType.LIFELINK, 0);
        assertCounterCount(playerA, sorinPW, CounterType.LOYALTY, 1);
    }

    @Test
    public void test_Minus6_OtherWhite() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sorinPW);
        addCard(Zone.BATTLEFIELD, playerA, "Baneslayer Angel"); // notably white
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, sorinPW, CounterType.LOYALTY, 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-6");
        addTarget(playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertSubtype("Elite Vanguard", SubType.HUMAN);
        assertSubtype("Elite Vanguard", SubType.VAMPIRE); // Vampire in addition
        assertCounterCount(playerA, "Elite Vanguard", CounterType.LIFELINK, 1);
        assertCounterCount(playerA, sorinPW, CounterType.LOYALTY, 1);
    }
}
