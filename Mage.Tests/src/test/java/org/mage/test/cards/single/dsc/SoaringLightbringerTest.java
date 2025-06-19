package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author notgreat
 */
public class SoaringLightbringerTest extends CardTestCommander4Players {

    @Test
    public void test_AttacksDoubled() {
        addCard(Zone.BATTLEFIELD, playerA, "Soaring Lightbringer");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        attack(1, playerA, "Soaring Lightbringer", playerB);
        attack(1, playerA, "Memnite", playerB);


        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Glimmer Token", true, 1);
        assertLife(playerB, 20 - 4 - 1 - 1);
    }

    @Test
    public void test_AttacksTwo() {
        addCard(Zone.BATTLEFIELD, playerA, "Soaring Lightbringer");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        attack(1, playerA, "Soaring Lightbringer", playerB);
        attack(1, playerA, "Memnite", playerD);

        setChoice(playerA, "Whenever"); // Order triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Glimmer Token", true, 2);
        assertLife(playerB, 20 - 4 - 1);
        assertLife(playerD, 20 - 1 - 1);
    }

    @Test
    public void test_AttacksPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerD, "Soaring Lightbringer");
        addCard(Zone.BATTLEFIELD, playerD, "Memnite");
        addCard(Zone.HAND, playerA, "Nissa Revane");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nissa Revane");
        attack(2, playerD, "Memnite", "Nissa Revane");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerD, "Glimmer Token", 0);
        assertCounterCount("Nissa Revane", CounterType.LOYALTY, 1);
    }

    @Test
    public void test_AttacksEnters() {
        addCard(Zone.BATTLEFIELD, playerA, "Soaring Lightbringer");
        addCard(Zone.BATTLEFIELD, playerA, "Falconer Adept");

        attack(1, playerA, "Falconer Adept", playerB);
        setChoice(playerA, "Whenever"); // Order triggers
        addTarget(playerA, playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Glimmer Token", true, 1);
        assertTappedCount("Bird Token", true, 1);
        assertLife(playerB, 20 - 2 - 1);
        assertLife(playerC, 20 - 1);
    }
}
