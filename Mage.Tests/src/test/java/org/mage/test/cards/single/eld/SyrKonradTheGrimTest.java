package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SyrKonradTheGrimTest extends CardTestPlayerBase {

    @Test
    public void leavesOwnGraveyardTriggerTest() {
        addCard(Zone.HAND, playerA, "Rest in Peace");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Syr Konrad, the Grim");
        // These leaving the graveyard *should* cause loss of life
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);
        // These ones *shouldn't*
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears");
        setStopAt(1, PhaseStep.UNTAP);
        execute();

        assertLife(playerB, 20);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rest in Peace");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 0);
        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }
}
