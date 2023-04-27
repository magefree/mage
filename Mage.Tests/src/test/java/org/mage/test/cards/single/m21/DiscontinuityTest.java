package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DiscontinuityTest extends CardTestPlayerBase {

    @Test
    public void testCostReduction(){
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // As long as it's your turn, this spell costs {2}{U}{U} less to cast.
        addCard(Zone.HAND, playerA, "Discontinuity");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discontinuity");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertTappedCount("Island", true, 2);
    }
}
