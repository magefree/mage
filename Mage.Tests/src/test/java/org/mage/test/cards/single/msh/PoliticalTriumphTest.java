package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PoliticalTriumphTest extends CardTestPlayerBase {

    /**
     * Political Triumph {W}
     * Enchantment — Plan
     * Whenever a creature you control enters, scry 1 and put a plan counter on this enchantment.
     * When the fourth plan counter is put on this enchantment, sacrifice it, draw a card, and put a +1/+1 counter on
     * each creature you control.
     */
    private static final String triumph = "Political Triumph";

    @Test
    public void test_CreatureEnters_ScryAndCounter() {
        addCard(Zone.BATTLEFIELD, playerA, triumph);
        //
        addCard(Zone.HAND, playerA, "Memnite"); // {0}
        addCard(Zone.HAND, playerB, "Ornithopter"); // {0}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top

        // an opponent's creature doesn't count
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Ornithopter", 1);
        assertCounterCount(playerA, triumph, CounterType.PLAN, 1);
    }

    @Test
    public void test_ThirdCounter_NoSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, triumph);
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W}, instant
        addCard(Zone.HAND, playerA, "Silvercoat Lion"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        setChoice(playerA, "Whenever a creature you control enters"); // two triggers order
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, triumph, CounterType.PLAN, 3);
        assertPowerToughness(playerA, "Soldier Token", 1, 1, Filter.ComparisonScope.All);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
    }

    @Test
    public void test_FourthCounter_SacrificeDrawAndCounters() {
        addCard(Zone.BATTLEFIELD, playerA, triumph);
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Raise the Alarm", 2); // {1}{W}, instant
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        setChoice(playerA, "Whenever a creature you control enters"); // two triggers order
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        setChoice(playerA, "Whenever a creature you control enters"); // two triggers order
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, triumph, 1);
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Soldier Token", 4);
        assertPowerToughness(playerA, "Soldier Token", 1 + 1, 1 + 1, Filter.ComparisonScope.All);
    }
}
