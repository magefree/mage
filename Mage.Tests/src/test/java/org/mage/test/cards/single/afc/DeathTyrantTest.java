package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DeathTyrantTest extends CardTestPlayerBase {

    @Test
    public void attackerDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Death Tyrant", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1);

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Hill Giant", "Grizzly Bears");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Zombie", 1);
        assertPermanentCount(playerA, "Death Tyrant", 1);
        assertPermanentCount(playerB, "Hill Giant", 1);
    }

    @Test
    public void blockerDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Death Tyrant", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        attack(1, playerA, "Hill Giant");
        block(1, playerB, "Grizzly Bears", "Hill Giant");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Zombie", 1);
        assertPermanentCount(playerA, "Death Tyrant", 1);
        assertPermanentCount(playerA, "Hill Giant", 1);
    }
}
