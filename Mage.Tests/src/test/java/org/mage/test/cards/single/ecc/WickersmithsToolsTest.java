package org.mage.test.cards.single.ecc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WickersmithsToolsTest extends CardTestPlayerBase {

    @Test
    public void testSacrificeCreatesTappedScarecrows() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Wickersmith's Tools");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 5);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wickersmith's Tools", CounterType.CHARGE, 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}, {T}, Sacrifice {this}: Create X");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Wickersmith's Tools", 1);
        assertPermanentCount(playerA, "Scarecrow Token", 2);
        assertTappedCount("Scarecrow Token", true, 2);
    }
}
