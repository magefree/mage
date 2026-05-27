package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SeiferAlmasyTest extends CardTestPlayerBase {

    @Test
    public void testDoubleStrike() {
        addCard(Zone.BATTLEFIELD, playerA, "Seifer Almasy");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");

        attack(1, playerA, "Balduvian Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 16);
    }

}
