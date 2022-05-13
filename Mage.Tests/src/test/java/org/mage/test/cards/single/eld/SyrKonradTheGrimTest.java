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

    @Test
    public void reanimateFromOtherGraveyardTest() {
        addCard(Zone.HAND, playerA, "Reanimate");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Syr Konrad, the Grim");
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears");

        setStopAt(1, PhaseStep.UNTAP);
        execute();

        assertLife(playerB, 20);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Grizzly Bears");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 18); // loss of 2 from reanimate
        assertLife(playerB, 20); // card leaving B's graveyard *shouldn't* cause loss of life
    }
}
