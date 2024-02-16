package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ForgottenSentinelTest extends CardTestPlayerBase {

    @Test
    public void etbTapped(){
        addCard(Zone.HAND, playerA, "Forgotten Sentinel");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forgotten Sentinel");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertTapped("Forgotten Sentinel", true);
    }
}
