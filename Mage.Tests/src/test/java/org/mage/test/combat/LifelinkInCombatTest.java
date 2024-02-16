package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LifelinkInCombatTest extends CardTestPlayerBase {
    @Test
    public void testOneBlockerTrample() {
        // 4/4 Trample Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Elderwood Scion");
        // Whenever you gain life, put a +1/+1 counter on target creature or enchantment you control.
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, Sun-Crowned");
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(1, playerA, "Elderwood Scion");
        block(1, playerB, "Grizzly Bears", "Elderwood Scion");
        setChoice(playerA, "X=2"); // Damage to Grizzly Bears
        addTarget(playerA, "Elderwood Scion");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Elderwood Scion", 5, 5);
        assertLife(playerA, 24);
        assertLife(playerB, 18);
    }


    @Test
    public void testTwoBlockers() {
        // 4/4 Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Brion Stoutarm");
        // Whenever you gain life, put a +1/+1 counter on target creature or enchantment you control.
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, Sun-Crowned");
        // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Boros Recruit", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Suntail Hawk", 1);

        attack(1, playerA, "Brion Stoutarm");
        block(1, playerB, "Boros Recruit", "Brion Stoutarm");
        block(1, playerB, "Suntail Hawk", "Brion Stoutarm");
        setChoice(playerA, "X=1"); // Damage assignment
        setChoice(playerA, "X=1"); // Damage assignment
        addTarget(playerA, "Brion Stoutarm");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Brion Stoutarm", 5, 5);
        assertLife(playerA, 24);
        assertLife(playerB, 20);
    }


    @Test
    public void testTwoBlockersTrample() {
        // 4/4 Trample Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Elderwood Scion");
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, Sun-Crowned");
        // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Boros Recruit", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Suntail Hawk", 1);

        attack(1, playerA, "Elderwood Scion");
        block(1, playerB, "Boros Recruit", "Elderwood Scion");
        block(1, playerB, "Suntail Hawk", "Elderwood Scion");
        setChoice(playerA, "X=1"); // Damage assignment
        setChoice(playerA, "X=1"); // Damage assignment
        addTarget(playerA, "Elderwood Scion");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Elderwood Scion", 5, 5);
        assertLife(playerA, 24);
        assertLife(playerB, 18);
    }

    @Test
    public void testDamagePrevented() {
        // 2/2 Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Ajani's Sunstriker");
        // Whenever you gain life, put a +1/+1 counter on target creature or enchantment you control.
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, Sun-Crowned");
        // 1/1 Protection from creatures
        addCard(Zone.BATTLEFIELD, playerB, "Beloved Chaplain");

        attack(1, playerA, "Ajani's Sunstriker");
        block(1, playerB, "Beloved Chaplain", "Ajani's Sunstriker");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Ajani's Sunstriker", 2, 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testSomeDamagePreventedTrample() {
        // 4/4 Lifelink Trample
        addCard(Zone.BATTLEFIELD, playerA, "Elderwood Scion");
        // Whenever you gain life, put a +1/+1 counter on target creature or enchantment you control.
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, Sun-Crowned");
        // 1/1 Protection from creatures
        addCard(Zone.BATTLEFIELD, playerB, "Beloved Chaplain");

        attack(1, playerA, "Elderwood Scion");
        block(1, playerB, "Beloved Chaplain", "Elderwood Scion");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Elderwood Scion");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Elderwood Scion", 5, 5);
        assertLife(playerA, 23);
        assertLife(playerB, 17);
    }

    @Test
    public void testPlayerDamagePrevented() {
        // 4/4 Lifelink Trample
        addCard(Zone.BATTLEFIELD, playerA, "Elderwood Scion");
        // Whenever you gain life, put a +1/+1 counter on target creature or enchantment you control.
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, Sun-Crowned");
        // 3/3
        addCard(Zone.HAND, playerB, "Seht's Tiger");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);

        castSpell(1, PhaseStep.UPKEEP, playerB, "Seht's Tiger");
        setChoice(playerB, "Green");

        attack(1, playerA, "Elderwood Scion");
        block(1, playerB, "Seht's Tiger", "Elderwood Scion");
        setChoice(playerA, "X=3");
        addTarget(playerA, "Elderwood Scion");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Elderwood Scion", 5, 5);
        assertLife(playerA, 23);
        assertLife(playerB, 20);
    }
}
