package org.mage.test.cards.single.dom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class HaphazardBombardmentTest extends CardTestPlayerBase {

    @Test
    public void test_DestroyAtRandom() {
        // When this enchantment enters, choose four nonenchantment permanents you don't control and put an aim counter
        // on each of them.
        // At the beginning of your end step, if two or more permanents you don't control have an aim counter on them,
        // destroy one of those permanents at random.
        addCard(Zone.HAND, playerA, "Haphazard Bombardment"); // {5}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 4); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Haphazard Bombardment");
        checkPermanentCounters("aim", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", CounterType.AIM, 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 4 - 1);
    }
}
