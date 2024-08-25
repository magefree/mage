package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PrimalPrayersTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PrimalPrayers Primal Prayers} {2}{G}{G}
     * Enchantment
     * When Primal Prayers enters the battlefield, you get {E}{E} (two energy counters).
     * You may cast creature spells with mana value 3 or less by paying {E} rather than paying their mana costs. If you cast a spell this way, you may cast it as though it had flash.
     */
    private static final String prayers = "Primal Prayers";

    private static void checkEnergyCount(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getCountersCount(CounterType.ENERGY));
    }

    @Test
    public void test_DoesntGiveFlash_RegularCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, prayers);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        checkPlayableAbility("1: regular cast at sorcery", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Grizzly Bears", true);
        checkPlayableAbility("2: not able to use regular cast at wrong timing", 1, PhaseStep.BEGIN_COMBAT, playerA,
                "Cast Grizzly Bears", false);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
    }

    @Test
    public void test_GiveFlash_EnergyAlternativeCost() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, prayers);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prayers, true);
        checkPlayableAbility("1: alternative cast at sorcery", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Grizzly Bears", true);
        checkPlayableAbility("2: able to use alternative cast at instant timing", 1, PhaseStep.BEGIN_COMBAT, playerA,
                "Cast Grizzly Bears", true);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", true);
        setChoice(playerA, "Cast with alternative cost: Pay {E}");

        runCode("energy counter is 1", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> checkEnergyCount(info, player, 1));

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_UseEnergy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 3);
        addCard(Zone.HAND, playerA, prayers);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prayers, true);
        runCode("1: energy counter is 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        setChoice(playerA, "Cast with alternative cost: Pay {E}"); // alternative cost chosen
        runCode("2: energy counter is 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 1));

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        setChoice(playerA, "Cast with alternative cost: Pay {E}"); // alternative cost chosen
        checkPlayableAbility("no more energy to cast third Bears", 1, PhaseStep.BEGIN_COMBAT, playerA,
                "Cast Grizzly Bears", false);
        runCode("3: energy counter is 0", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 0));

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_PayManaStill() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, prayers);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prayers, true);
        runCode("1: energy counter is 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        setChoice(playerA, TestPlayer.CHOICE_NORMAL_COST);
        runCode("2: energy counter is still 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Forest", true, 6);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_CanNotCastWithoutEnergyAsFlash() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, prayers);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prayers, true);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears");
        setChoice(playerA, TestPlayer.CHOICE_NORMAL_COST); // is not a valid choice
        runCode("energy counter is 2", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));

        setStopAt(1, PhaseStep.END_COMBAT);
        try {
            execute();
            throw new IllegalStateException("Execute went without error");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Choose an alternative cost")) {
                Assert.fail("Should have thrown error about missing the choice for the alternative cost, but got:\n" + e.getMessage());
            }
        }
    }

    @Test
    public void test_CanNotCastWithOmniscienceAsFlash() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, prayers);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prayers, true);
        setChoice(playerA, "Cast without paying its mana cost (source: Omniscience"); // can choose Omniscience at sorcery speed.
        runCode("energy counter is 2", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", true);
        //setChoice(playerA, "Cast without paying its mana cost (source: Omniscience"); // can not choose Omniscience at instant speed.

        setStopAt(1, PhaseStep.END_COMBAT);
        try {
            execute();
            throw new IllegalStateException("Execute went without error");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Choose an alternative cost")) {
                Assert.fail("Should have thrown error about missing the choice for the alternative cost, but got:\n" + e.getMessage());
            }
        }
    }
}
