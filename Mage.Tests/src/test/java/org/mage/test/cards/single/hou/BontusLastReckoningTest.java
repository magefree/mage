package org.mage.test.cards.single.hou;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BontusLastReckoningTest extends CardTestPlayerBase {

    private String reckoning = "Bontu's Last Reckoning";

    @Test
    public void testDelayedUntap(){
        String pouncer = "Adorned Pouncer";
        String angel = "Angel of Condemnation";
        addCard(Zone.HAND, playerA, reckoning);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, pouncer);
       addCard(Zone.BATTLEFIELD, playerB, angel);
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN,playerA, reckoning);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertTappedCount("Swamp", true, 3);
        assertTappedCount("Island", false, 1);
        assertGraveyardCount(playerA, pouncer, 1);
        assertGraveyardCount(playerB, angel, 1);
    }
}
