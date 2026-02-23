package org.mage.test.cards.single.mh1;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class UnboundFlourishingTest extends CardTestPlayerBase {
    @Test
    public void testCastWanShiTong() {
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Wan Shi Tong, Librarian");
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wan Shi Tong, Librarian");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Wan Shi Tong, Librarian", 1);
        assertPowerToughness(playerA, "Wan Shi Tong, Librarian", 5, 5);
    }

    @Test
    public void testDiscoverWanShiTong() {
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Wan Shi Tong, Librarian");
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing");
        addCard(Zone.HAND, playerA, "Geological Appraiser");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geological Appraiser");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Geological Appraiser", 1);
        assertPermanentCount(playerA, "Wan Shi Tong, Librarian", 1);
        assertPowerToughness(playerA, "Wan Shi Tong, Librarian", 1, 1);
    }
}
