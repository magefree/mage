package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

public class NexusMentalityTest extends CardTestCommanderDuelBase {

    @Test
    public void testCommanderAllowsBothModesMoveCountersThenRemoveAndDraw() {
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.HAND, playerA, "Nexus Mentality");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Everflowing Chalice");
        addCard(Zone.LIBRARY, playerA, "Healing Salve", 3);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", CounterType.P1P1, 2);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Everflowing Chalice", CounterType.CHARGE, 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nexus Mentality");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        addTarget(playerA, "Memnite");
        addTarget(playerA, "Ornithopter");
        addTarget(playerA, "Everflowing Chalice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertCounterCount(playerA, "Memnite", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Ornithopter", CounterType.P1P1, 2);
        assertCounterCount(playerA, "Everflowing Chalice", CounterType.CHARGE, 0);
        assertHandCount(playerA, 3);
    }
}
