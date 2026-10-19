package org.mage.test.cards.single.yone;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class QuicksilverLapidaryTest extends CardTestPlayerBase {

    @Test
    public void testConjuredMoxCanBeCastAndBlinkConjuresAgain() {
        addCard(Zone.HAND, playerA, "Quicksilver Lapidary");
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Quicksilver Lapidary");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Opal");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Quicksilver Lapidary");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Mox Opal", 1);
        assertHandCount(playerA, "Mox Opal", 1);
        assertHandCount(playerB, "Mox Opal", 0);
    }
}
