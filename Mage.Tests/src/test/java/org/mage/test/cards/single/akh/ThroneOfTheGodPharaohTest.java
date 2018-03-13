package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ThroneOfTheGodPharaohTest extends CardTestPlayerBase {

    String throne = "Throne of the God-Pharaoh";

    @Test
    public void testMyTurn() {
        addCard(Zone.BATTLEFIELD, playerA, throne);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(1, playerA, "Grizzly Bears", playerB);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 17);
        assertLife(playerA, 20);
    }

    @Test
    public void testOpponentsTurn() {
        addCard(Zone.BATTLEFIELD, playerA, throne);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(1, playerA, "Grizzly Bears", playerB);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 17);
        assertLife(playerA, 20);

    }


}
