package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CancelTest extends CardTestPlayerBase {

    @Test
    public void counterTargetSpell(){
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // {1}{U}{U} Counter target spell
        addCard(Zone.HAND, playerA, "Cancel");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerB, "Grizzly Bears");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cancel", "Grizzly Bears");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Cancel", 1);


    }
}
