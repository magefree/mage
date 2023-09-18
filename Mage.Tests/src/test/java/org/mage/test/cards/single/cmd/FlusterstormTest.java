package org.mage.test.cards.single.cmd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FlusterstormTest extends CardTestPlayerBase {

    @Test
    public void testThatFlusterstormGoesToGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Spark Spray");

        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerB, "Flusterstorm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Spray", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Flusterstorm", "Spark Spray", "Spark Spray");

        setChoice(playerA, false);
        setChoice(playerB, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
