package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.ImpendingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ImpendingTest extends CardTestPlayerBase {

    private static final String hauntwoods = "Overlord of the Hauntwoods";

    public void assertHasImpending(String name, boolean hasAbility) {
        Permanent permanent = getPermanent(name);
        Assert.assertEquals(
                "Should" + (hasAbility ? "" : "n't") + " have Impending ability",
                hasAbility, permanent.getAbilities(currentGame).containsClass(ImpendingAbility.class)
        );
    }

    @Test
    public void testCastRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with no");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, true);
        assertSubtype(hauntwoods, SubType.AVATAR);
        assertSubtype(hauntwoods, SubType.HORROR);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 0);
        assertPowerToughness(playerA, hauntwoods, 6, 5);
        assertHasImpending(hauntwoods, true);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    @Test
    public void testCastImpending() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, false);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 4);
        assertHasImpending(hauntwoods, true);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    @Test
    public void testImpendingRemoveCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, false);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 4 - 1);
        assertHasImpending(hauntwoods, true);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    @Test
    public void testCastImpendingRemoveAllCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, true);
        assertSubtype(hauntwoods, SubType.AVATAR);
        assertSubtype(hauntwoods, SubType.HORROR);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 0);
        assertPowerToughness(playerA, hauntwoods, 6, 5);
        assertHasImpending(hauntwoods, false);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    private static final String hexmage = "Vampire Hexmage";

    @Test
    public void testCastImpendingHexmage() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, hexmage);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Sacrifice", hauntwoods);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, true);
        assertSubtype(hauntwoods, SubType.AVATAR);
        assertSubtype(hauntwoods, SubType.HORROR);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 0);
        assertPowerToughness(playerA, hauntwoods, 6, 5);
        assertHasImpending(hauntwoods, true);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    @Test
    public void testCastImpendingHexmageNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, hexmage);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Sacrifice", hauntwoods);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, true);
        assertSubtype(hauntwoods, SubType.AVATAR);
        assertSubtype(hauntwoods, SubType.HORROR);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 0);
        assertPowerToughness(playerA, hauntwoods, 6, 5);
        assertHasImpending(hauntwoods, false);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    private static final String solemnity = "Solemnity";

    @Test
    public void testCastImpendingSolemnity() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, solemnity);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, true);
        assertSubtype(hauntwoods, SubType.AVATAR);
        assertSubtype(hauntwoods, SubType.HORROR);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 0);
        assertPowerToughness(playerA, hauntwoods, 6, 5);
        assertHasImpending(hauntwoods, true);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    @Test
    public void testCastImpendingSolemnityNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, solemnity);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, true);
        assertSubtype(hauntwoods, SubType.AVATAR);
        assertSubtype(hauntwoods, SubType.HORROR);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 0);
        assertPowerToughness(playerA, hauntwoods, 6, 5);
        assertHasImpending(hauntwoods, false);

        assertPermanentCount(playerA, "Everywhere", 1);
    }
}
