package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class EligethCrossroadsAugurTest extends CardTestPlayerBase {

    @Test
    public void test_Playable() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 2);

        // If you would scry a number of cards, draw that many cards instead.
        addCard(Zone.BATTLEFIELD, playerA, "Eligeth, Crossroads Augur", 1);
        //
        // When Faerie Seer enters the battlefield, scry 2.
        addCard(Zone.HAND, playerA, "Faerie Seer", 1); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // scry multiple cards and draws instead
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faerie Seer");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Faerie Seer", 1);
        assertHandCount(playerA, "Balduvian Bears", 2);
    }
}
