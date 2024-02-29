package org.mage.test.cards.single.lcc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DominionSpy
 */
public class RipplesOfPotentialTest extends CardTestPlayerBase {

    /**
     * Ripples of Potential
     * {1}{U}
     * Instant
     * Proliferate, then choose any number of permanents you control that had a counter put on them this way. Those permanents phase out.
     */
    @Test
    public void test_RipplesOfPotential() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Blast Zone");
        addCard(Zone.BATTLEFIELD, playerA, "Arcbound Javelineer");
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Pyromaster");
        addCard(Zone.BATTLEFIELD, playerA, "Atraxa's Skitterfang");

        addCard(Zone.HAND, playerA, "Ripples of Potential");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ripples of Potential");
        // Proliferate choice
        setChoice(playerA, "Blast Zone^Arcbound Javelineer^Chandra, Pyromaster^Atraxa's Skitterfang");
        // Phase out choice
        setChoice(playerA, "Blast Zone^Arcbound Javelineer^Chandra, Pyromaster");

        setStrictChooseMode(true);
        setChoice(playerA, false); // Atraxa's Skitterfang ability
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Blast Zone", 0);
        assertPermanentCount(playerA, "Arcbound Javelineer", 0);
        assertPermanentCount(playerA, "Chandra, Pyromaster", 0);
        assertPermanentCount(playerA, "Atraxa's Skitterfang", 1);
        assertCounterCount("Atraxa's Skitterfang", CounterType.OIL, 4);
    }
}
