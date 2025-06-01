package org.mage.test.cards.single.tor;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class SternJudgeTest extends CardTestPlayerBase {

    @Test
    public void testCounts() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Stern Judge");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Each player loses");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 18);
    }

}
