package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FirstStrikeTest extends CardTestPlayerBase {


    @Test
    public void firstStrikeAttacker(){
        addCard(Zone.BATTLEFIELD, playerA, "Silver Knight", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        attack(1, playerA, "Silver Knight");
        block(1, playerB, "Grizzly Bears", "Silver Knight");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Silver Knight", 0);
    }

    @Test
    public void firstStrikeBlocker(){
        addCard(Zone.BATTLEFIELD, playerB, "Silver Knight", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Silver Knight", "Grizzly Bears");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Silver Knight", 0);
    }
}
