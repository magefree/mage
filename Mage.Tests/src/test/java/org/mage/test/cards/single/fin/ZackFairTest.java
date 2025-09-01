package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ZackFairTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.z.ZackFair Zack Fair} {W}
     * Legendary Creature — Human Soldier
     * Zack Fair enters with a +1/+1 counter on it.
     * {1}, Sacrifice Zack Fair: Target creature you control gains indestructible until end of turn. Put Zack Fair’s counters on that creature and attach an Equipment that was attached to Zack Fair to that creature.
     * 0/1
     */
    private static final String zack = "Zack Fair";

    @Test
    public void test_NoEquip() {
        addCard(Zone.HAND, playerA, zack, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, zack, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}", "Squire");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Squire", CounterType.P1P1, 1);
    }

    @Test
    public void test_OtherCountersToo() {
        addCard(Zone.HAND, playerA, zack, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Squire");
        /**
         * Lifelink
         * Cycling {1}{W} ({1}{W}, Discard this card: Draw a card.)
         * When you cycle this card, put a lifelink counter on target creature you control.
         */
        addCard(Zone.HAND, playerA, "Splendor Mare");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, zack, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling ");
        addTarget(playerA, zack);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}", "Squire");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Squire", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Squire", CounterType.LIFELINK, 1);
    }

    @Test
    public void test_OneEquip() {
        addCard(Zone.HAND, playerA, zack, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Short Sword");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, zack, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", zack);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice", "Squire");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Squire", CounterType.P1P1, 1);
        assertAttachedTo(playerA, "Short Sword", "Squire", true);
    }

    @Test
    public void test_TwoEquip() {
        addCard(Zone.HAND, playerA, zack, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Short Sword");
        addCard(Zone.BATTLEFIELD, playerA, "Golem-Skin Gauntlets");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, zack, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", zack);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", zack);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice", "Squire");

        setChoice(playerA, "Short Sword");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Squire", CounterType.P1P1, 1);
        assertAttachedTo(playerA, "Short Sword", "Squire", true);
        assertAttachedTo(playerA, "Golem-Skin Gauntlets", "Squire", false);
    }
}
