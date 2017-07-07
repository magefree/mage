package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MycosynthLatticeTest extends CardTestPlayerBase {


    @Test
    public void testMycoSynthLatticeWithCrystallineCrawler(){
        String crawler = "Crystalline Crawler";
        String lattice = "Mycosynth Lattice";

        addCard(Zone.BATTLEFIELD, playerA, lattice);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, crawler);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, crawler);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertCounterCount(playerA, crawler, CounterType.P1P1, 4);
    }
}
