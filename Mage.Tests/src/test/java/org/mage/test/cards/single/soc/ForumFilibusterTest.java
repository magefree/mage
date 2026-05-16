package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ForumFilibusterTest extends CardTestPlayerBase {

    @Test
    public void testUpkeepTokenAndReflexiveAttachAuraFromGraveyard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forum Filibuster");
        addCard(Zone.GRAVEYARD, playerA, "Rancor");

        addTarget(playerA, "Rancor");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Inkling Token", 1);
        assertPermanentCount(playerA, "Rancor", 1);
        assertGraveyardCount(playerA, "Rancor", 0);
        assertPowerToughness(playerA, "Inkling Token", 4, 1);
    }
}
