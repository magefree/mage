package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AirbendTest extends CardTestPlayerBase {

    @Test
    public void testAirbendSpell() {
        addCard(Zone.HAND, playerA, "Aang, Swift Savior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aang, Swift Savior", null, "Lightning Bolt");
        addTarget(playerA, "Lightning Bolt");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Aang, Swift Savior", 1);
        assertExileCount(playerB, "Lightning Bolt", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertTappedCount("Mountain", true, 1);
        assertTappedCount("Wastes", true, 2);
        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20);
    }
}
