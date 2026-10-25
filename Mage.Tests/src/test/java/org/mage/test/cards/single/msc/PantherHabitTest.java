package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class PantherHabitTest extends CardTestPlayerBase {

    // If equipped creature would be dealt damage, prevent that damage and put that many +1/+1 counters on it.
    private static final String habit = "Panther Habit";

    private static final String lion = "Silvercoat Lion"; // 2/2
    private static final String bear = "Grizzly Bears"; // 2/2

    @Test
    public void test_PreventDamageToEquippedCreatureAndPutCounters() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, habit);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // equip {2} + bolt {R}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", lion);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertCounterCount(playerA, lion, CounterType.P1P1, 3);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 3);
        Assert.assertEquals("damage must be prevented", 0, getPermanent(lion, playerA).getDamage());
    }

    @Test
    public void test_OtherCreaturesAreNotProtected() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, habit);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // equip {2} + bolt {R}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", bear);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertCounterCount(playerA, lion, CounterType.P1P1, 0);
    }

    @Test
    public void test_CombatDamageToEquippedCreature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, habit);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);

        attack(2, playerB, bear);
        block(2, playerA, lion, bear);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertCounterCount(playerA, lion, CounterType.P1P1, 2);
        assertGraveyardCount(playerB, bear, 1);
    }

    @Test
    public void test_DamageCantBePrevented_CountersAreStillPut() {
        // same wording as Gatta and Luzzu, ruling 2025-06-06:
        // if damage that can't be prevented is dealt to that creature, you still put that many +1/+1 counters on it
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, habit);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Banefire deals X damage to any target. If X is 5 or more, the damage can't be prevented.
        addCard(Zone.HAND, playerA, "Banefire");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banefire", lion);
        setChoice(playerA, "X=5");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2/2 gets five +1/+1 counters and then is dealt 5 damage it can't prevent, so it survives as a 7/7
        assertPermanentCount(playerA, lion, 1);
        assertCounterCount(playerA, lion, CounterType.P1P1, 5);
        assertPowerToughness(playerA, lion, 2 + 5, 2 + 5);
        Assert.assertEquals("damage can't be prevented", 5, getPermanent(lion, playerA).getDamage());
    }
}
