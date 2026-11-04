package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class WindshaperPlanetarTest extends CardTestCommander4Players {

    @Test
    public void testNoTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Humble Budoka"); // shroud
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerB, "Windshaper Planetar");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);

        attack(1, playerA, "Humble Budoka", playerB);
        attack(1, playerA, "Balduvian Bears", playerB);

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Windshaper Planetar");
        setChoice(playerB, true);
        addTarget(playerB, playerC);
        addTarget(playerB, playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerC, 16);
    }
}
