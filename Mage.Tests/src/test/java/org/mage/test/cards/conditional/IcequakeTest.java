package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IcequakeTest extends CardTestPlayerBase {

    @Test
    public void testIcequakeOnSnowLand(){

        addCard(Zone.BATTLEFIELD, playerB, "Snow-Covered Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp",10);
        addCard(Zone.HAND, playerA, "Icequake");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN,playerA, "Icequake", "Snow-Covered Plains");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }
}
