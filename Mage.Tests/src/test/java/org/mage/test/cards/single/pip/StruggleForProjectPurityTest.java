package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class StruggleForProjectPurityTest extends CardTestCommander4Players {

    /**
     * {@link mage.cards.s.StruggleForProjectPurity Struggle for Project Purity}
     * {3}{U}
     * Enchantment
     * As Struggle for Project Purity enters, choose Brotherhood or Enclave.
     * • Brotherhood — At the beginning of your upkeep, each opponent draws a card. You draw a card for each card drawn this way.
     * • Enclave — Whenever a player attacks you with one or more creatures, that player gets twice that many rad counters.
     */
    private static final String struggle = "Struggle for Project Purity";

    private void checkRadCounters(String info, int needA, int needB, int needC, int needD) {
        Assert.assertEquals(info + ", rad counter on playerA", needA, playerA.getCountersCount(CounterType.RAD));
        Assert.assertEquals(info + ", rad counter on playerB", needB, playerB.getCountersCount(CounterType.RAD));
        Assert.assertEquals(info + ", rad counter on playerC", needC, playerC.getCountersCount(CounterType.RAD));
        Assert.assertEquals(info + ", rad counter on playerD", needD, playerD.getCountersCount(CounterType.RAD));
    }

    @Test
    public void test_Brotherhood() {
        // Player order: A -> D -> C -> B

        // Brotherhood — At the beginning of your upkeep, each opponent draws a card. You draw a card for each card drawn this way.
        addCard(Zone.HAND, playerA, struggle);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        checkHandCount("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // struggle + starting draw

        // turn 1 - A - prepare brotherhood, no triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, struggle);
        setChoice(playerA, "Brotherhood");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // turn 2 - D - no triggers
        checkHandCount("no draws on turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        // turn 3 - C - no triggers
        checkHandCount("no draws on turn 3", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        // turn 4 - B - no triggers
        checkHandCount("no draws on turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        // turn 5 - A - trigger
        // opponent draw: +1
        // you draw: +3
        checkHandCount("draws trigger", 5, PhaseStep.PRECOMBAT_MAIN, playerA, 1 + 1 + 3); // draw 1 + draw 5 + draw trigger
        checkHandCount("draws trigger", 5, PhaseStep.PRECOMBAT_MAIN, playerB, 2); // opponent turn + trigger
        checkHandCount("draws trigger", 5, PhaseStep.PRECOMBAT_MAIN, playerC, 2); // opponent turn + trigger
        checkHandCount("draws trigger", 5, PhaseStep.PRECOMBAT_MAIN, playerD, 2); // opponent turn + trigger

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, struggle, 1);
    }

    @Test
    public void test_Enclave() {
        // Player order: A -> D -> C -> B

        // Enclave — Whenever a player attacks you with one or more creatures, that player gets twice that many rad counters.
        addCard(Zone.HAND, playerA, struggle);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears", 2);

        checkHandCount("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // struggle + starting draw
        runCode("rad count playerA turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounters(info, 0, 0, 0, 0));

        // turn 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, struggle);
        setChoice(playerA, "Enclave");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // turn 1
        attack(1, playerA, "Grizzly Bears", playerD);
        attack(1, playerA, "Grizzly Bears", playerD);
        runCode("A attacked D on turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounters(info, 0, 0, 0, 0));

        // turn 2
        attack(2, playerD, "Grizzly Bears", playerA); // <<< trigger for D
        attack(2, playerD, "Grizzly Bears", playerA);
        runCode("D attacked A on turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounters(info, 0, 0, 0, 2 * 2));

        // turn 3
        attack(3, playerC, "Grizzly Bears", playerB);
        attack(3, playerC, "Grizzly Bears", playerB);
        runCode("B attacked B on turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounters(info, 0, 0, 0, 2 * 2));

        // turn 4
        attack(4, playerB, "Grizzly Bears", playerA); // <<< trigger for B
        attack(4, playerB, "Grizzly Bears", playerA);
        runCode("B attacked A on turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounters(info, 0, 2 * 2, 0, 2 * 2));

        // turn 5
        attack(5, playerA, "Grizzly Bears", playerD);
        attack(5, playerA, "Grizzly Bears", playerD);
        runCode("A attacked D on turn 5", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounters(info, 0, 2 * 2, 0, 2 * 2));

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, struggle, 1);
        assertLife(playerA, 20 - 2 * 2 - 2 * 2); // from D and B
        assertLife(playerB, 20 - 2 * 2); // from C
        assertLife(playerC, 20); // no attackers
        assertLife(playerD, 20 - 2 * 2 - 2 * 2); // from A and A
    }
}
