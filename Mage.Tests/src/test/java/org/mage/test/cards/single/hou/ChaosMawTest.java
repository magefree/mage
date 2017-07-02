package org.mage.test.cards.single.hou;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChaosMawTest extends CardTestPlayerBase {

    private String chaosMaw = "Chaos Maw";

    @Test
    public void testChaosMaw(){
        addCard(Zone.HAND, playerA, chaosMaw, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Savannah Lions", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chaosMaw );

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Savannah Lions", 1);
    }
}
