package org.mage.test.cards.single.hou;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HourOfRevelationTest extends CardTestPlayerBase {

    private final String hour = "Hour of Revelation";

    @Test
    public void testCountAllPlayers() {
        addCard(Zone.HAND, playerA, hour, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 5);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hour);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTappedCount("Plains", true, 3);
    }
}
