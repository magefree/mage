package org.mage.test.cards.continuous;

import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.VariableManaCost;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class UnboundFlourishingTest extends CardTestPlayerBase {

    // Unbound Flourishing

    // Whenever you cast a permanent spell with a mana cost that contains {X}, double the value of X.

    // Whenever you cast an instant or sorcery spell or activate an ability, if that spell’s mana cost or that ability’s activation cost contains {X},
    // copy that spell or ability. You may choose new targets for the copy.

    @Test
    public void test_OnCastPermanent_MustDoubleX() {
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 1);
        //
        // Endless One enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Endless One", 1); // {X}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // cast with X=3, but double it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=3");
        checkPermanentCounters("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Endless One", CounterType.P1P1, 3 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OnCastPermanent_MustDoubleX_MultipleTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 2);
        //
        // Endless One enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Endless One", 1); // {X}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // cast with X=3, but double it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "Unbound Flourishing"); // choose replacement effects
        setChoice(playerA, "X=3");
        checkPermanentCounters("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Endless One", CounterType.P1P1, 3 * 2 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OnCastInstantOrSourcery_MustCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 1);
        //
        // Banefire deals X damage to any target.
        addCard(Zone.HAND, playerA, "Banefire", 1); // {X}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // cast with X=3 and make copy with another target, not double X
        checkLife("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banefire", playerA);
        setChoice(playerA, "X=3");
        setChoice(playerA, true); // change target
        addTarget(playerA, playerB); // change to B
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 - 3); // original damage
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerB, 20 - 3); // copy damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OnCastPermanent_MustIgnoreAdditionCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 1);
        //
        // As an additional cost to cast this spell, pay X life.
        // Each other player loses X life.
        addCard(Zone.HAND, playerA, "Bond of Agony", 1); // {X}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // cast with X=3, pay addition (normal X) and apply effect (double X)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bond of Agony");
        setChoice(playerA, "X=3");
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 - 3); // addition cost X
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerB, 20 - 3 * 2); // damage double X

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OnActivatedAbility_MustCopy1() {
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 1);
        //
        // {X}: Put X tower counters on Helix Pinnacle.
        addCard(Zone.BATTLEFIELD, playerA, "Helix Pinnacle", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // pux 3 counters two times
        checkPermanentCounters("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Helix Pinnacle", CounterType.TOWER, 0);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}:");
        setChoice(playerA, "X=3");
        // it haven't target to change
        checkPermanentCounters("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Helix Pinnacle", CounterType.TOWER, 3 + 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OnActivatedAbility_MustCopy1_MultipleTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 2);
        //
        // {X}: Put X tower counters on Helix Pinnacle.
        addCard(Zone.BATTLEFIELD, playerA, "Helix Pinnacle", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // pux 3 counters two times from two cards
        checkPermanentCounters("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Helix Pinnacle", CounterType.TOWER, 0);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}:");
        setChoice(playerA, "X=3");
        setChoice(playerA, "Whenever you cast an instant or sorcery spell"); // choose triggered abilities from two instances
        // it haven't target to change
        checkPermanentCounters("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Helix Pinnacle", CounterType.TOWER, 3 + 3 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OnActivatedAbility_MustCopy2() {
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 1);
        //
        // {X}{R}, {T}, Sacrifice Cinder Elemental: It deals X damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Cinder Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // activate with X=3 and make copy with another target, not double X
        checkLife("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{R}", playerA);
        setChoice(playerA, "X=3");
        setChoice(playerA, true); // change target
        addTarget(playerA, playerB); // change to B
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 - 3); // original damage
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerB, 20 - 3); // copy damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_VariableManaCost() {
        // VariableManaCost contains:
        // - number of {X} instances (1, 2, 3)
        // - final X value after all replace events
        // - total number of pays
        // getAmount() must return final X value
        // setAmount() must setup final X value

        // test example: {X}{X}, X=3, double X effect from replace event
        int xInstancesCount = 2;
        int xAnnouncedValue = 3;
        int xMultiplier = 2;
        VariableManaCost cost = new VariableManaCost(VariableCostType.NORMAL, xInstancesCount);
        cost.setAmount(xAnnouncedValue * xMultiplier, xAnnouncedValue * xInstancesCount, false);

        Assert.assertEquals("instances count", xInstancesCount, cost.getXInstancesCount());
        Assert.assertEquals("boosted X value", xAnnouncedValue * xMultiplier, cost.getAmount());
    }


    @Test
    public void test_MultipleXInstances() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Chalice of the Void enters the battlefield with X charge counters on it.
        // Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.
        addCard(Zone.HAND, playerA, "Chalice of the Void", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");
        checkPermanentCounters("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Chalice of the Void", CounterType.CHARGE, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

}