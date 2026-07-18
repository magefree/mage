package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SPDrPilotedByPeniTest extends CardTestPlayerBase {

    private static final String spdrPilotedByPeni = "SP//dr, Piloted by Peni";

    @Test
    public void testBasic() {
        addCard(Zone.HAND, playerA, spdrPilotedByPeni);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spdrPilotedByPeni);
        addTarget(playerA, spdrPilotedByPeni);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, spdrPilotedByPeni, CounterType.P1P1, 1);
    }

}
