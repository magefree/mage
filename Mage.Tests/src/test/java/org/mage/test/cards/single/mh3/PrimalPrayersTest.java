package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
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
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears");
        setChoice(playerA, true);

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
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        setChoice(playerA, true); // use energy cost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        setChoice(playerA, true); // use energy cost
        checkPlayableAbility("no more energy to cast third Bears", 1, PhaseStep.BEGIN_COMBAT, playerA,
                "Cast Grizzly Bears", false);

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
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        setChoice(playerA, false); // use regular cost

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Forest", true, 6);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }
}
