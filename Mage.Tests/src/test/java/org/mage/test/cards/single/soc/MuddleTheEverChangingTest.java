package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MuddleTheEverChangingTest extends CardTestPlayerBase {

    @Test
    public void testCopiesNonlegendaryCreatureAfterInstantOrSorcery() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Muddle, the Ever-Changing");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
    }
}
