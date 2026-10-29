package org.mage.test.cards.single.bot;

import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ModularAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class BlasterCombatDJTest extends CardTestPlayerBase {

    /*
     * Blaster, Combat DJ {3}{R}{G}
     * Legendary Artifact Creature — Robot
     * More Than Meets the Eye {1}{R}{G} (You may cast this card converted for {1}{R}{G}.)
     * Other nontoken artifact creatures and Vehicles you control have modular 1.
     * (They enter with an additional +1/+1 counter on them. When they die, you may put their +1/+1 counters on target artifact creature.)
     * Whenever you put one or more +1/+1 counters on Blaster, convert it.
     * 3/3
     *
     * Blaster, Morale Booster
     * Legendary Artifact
     * Modular 3
     * {X}, {T}: Move X +1/+1 counters from Blaster onto another target artifact. That artifact gains haste until end of turn.
     * If Blaster has no +1/+1 counters on it, convert it. Activate only as a sorcery.
     */
    private static final String blaster = "Blaster, Combat DJ";
    private static final String booster = "Blaster, Morale Booster";

    private static final String memnite = "Memnite"; // {0} 1/1 artifact creature
    private static final String ornithopter = "Ornithopter"; // {0} 0/2 artifact creature, flying
    private static final String copter = "Smuggler's Copter"; // {2} 3/3 artifact vehicle
    private static final String bears = "Grizzly Bears"; // {1}{G} 2/2 creature
    private static final String bolt = "Lightning Bolt";

    @Test
    public void test_GrantedModular_EntersWithCounterAndMovesOnDeath() {
        addCard(Zone.BATTLEFIELD, playerA, blaster);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, memnite);
        addCard(Zone.HAND, playerA, ornithopter);
        addCard(Zone.HAND, playerA, copter);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ornithopter);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, copter);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("memnite enters with one counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite, CounterType.P1P1, 1);
        checkPermanentCounters("vehicle enters with one counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, copter, CounterType.P1P1, 1);
        checkPermanentCounters("non-artifact creature gets no counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, bears, CounterType.P1P1, 0);
        checkAbility("memnite has modular", 1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite, ModularAbility.class, true);
        checkAbility("bears has no modular", 1, PhaseStep.PRECOMBAT_MAIN, playerA, bears, ModularAbility.class, false);
        checkAbility("blaster has no modular", 1, PhaseStep.PRECOMBAT_MAIN, playerA, blaster, ModularAbility.class, false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, memnite);
        setChoice(playerA, true); // use modular
        addTarget(playerA, ornithopter);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, memnite, 1);
        assertCounterCount(playerA, ornithopter, CounterType.P1P1, 2);
        assertPowerToughness(playerA, ornithopter, 2, 4);
        assertPermanentCount(playerA, blaster, 1);
        assertCounterCount(playerA, blaster, CounterType.P1P1, 0);
    }

    @Test
    public void test_ConvertsWhenCountersArePutOnIt() {
        addCard(Zone.BATTLEFIELD, playerA, blaster);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, memnite);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, memnite);
        setChoice(playerA, true); // use modular
        addTarget(playerA, blaster);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, blaster, 0);
        assertPermanentCount(playerA, booster, 1);
        // converting doesn't trigger the modular 3 of the back face
        assertCounterCount(playerA, booster, CounterType.P1P1, 1);
    }

    @Test
    public void test_CastFrontFace_NoCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, blaster);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blaster);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, blaster, 1);
        assertCounterCount(playerA, blaster, CounterType.P1P1, 0);
        assertPowerToughness(playerA, blaster, 3, 3);
    }

    @Test
    public void test_MoreThanMeetsTheEye_EntersWithThreeCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, blaster);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, booster + " using More than Meets the Eye");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, blaster, 0);
        assertPermanentCount(playerA, booster, 1);
        assertCounterCount(playerA, booster, CounterType.P1P1, 3);
        assertTappedCount("Mountain", true, 2);
        assertTappedCount("Forest", true, 1);
    }

    @Test
    public void test_MoraleBooster_MoveCountersAndConvertWhenEmpty() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, memnite);
        addCard(Zone.BATTLEFIELD, playerA, ornithopter);
        addCard(Zone.HAND, playerA, blaster);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, booster + " using More than Meets the Eye");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // X=2: two of the three counters go to Memnite
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}: Move", memnite);
        setChoice(playerA, "X=2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("memnite got two counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite, CounterType.P1P1, 2);
        checkAbility("memnite has haste", 1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite, HasteAbility.class, true);
        checkAbility("ornithopter has no haste", 1, PhaseStep.PRECOMBAT_MAIN, playerA, ornithopter, HasteAbility.class, false);
        checkPermanentCounters("one counter left", 1, PhaseStep.PRECOMBAT_MAIN, playerA, booster, CounterType.P1P1, 1);
        checkPermanentCount("not converted yet", 1, PhaseStep.PRECOMBAT_MAIN, playerA, booster, 1);
        checkAbility("haste ends at end of turn", 3, PhaseStep.UPKEEP, playerA, memnite, HasteAbility.class, false);

        // X=3 with only one counter left: all of them are moved, then Blaster converts
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}: Move", ornithopter);
        setChoice(playerA, "X=3");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, ornithopter, CounterType.P1P1, 1);
        assertAbility(playerA, ornithopter, HasteAbility.getInstance(), true);
        assertCounterCount(playerA, memnite, CounterType.P1P1, 2);
        assertPermanentCount(playerA, booster, 0);
        assertPermanentCount(playerA, blaster, 1);
        assertCounterCount(playerA, blaster, CounterType.P1P1, 0);
        assertPowerToughness(playerA, blaster, 3, 3);
    }
}
