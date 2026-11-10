package org.mage.test.cards.single.dom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HaphazardBombardmentTest extends CardTestPlayerBase {

    @Test
    public void testEndStepTrigger() {
        addCard(Zone.HAND, playerA, "Haphazard Bombardment");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Haphazard Bombardment");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Haphazard Bombardment", 1);
        assertPermanentCount(playerB, "Mountain", 3);
    }
}
