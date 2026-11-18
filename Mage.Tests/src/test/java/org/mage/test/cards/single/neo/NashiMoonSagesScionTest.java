package org.mage.test.cards.single.neo;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class NashiMoonSagesScionTest extends CardTestPlayerBase {

    @Test
    public void testSourceLeavingDoesNotAddPermission() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.BATTLEFIELD, playerA, "Nashi, Moon Sage's Scion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion");
        skipInitShuffling();

        attack(1, playerA, "Nashi, Moon Sage's Scion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Nashi, Moon Sage's Scion", true);

        checkPlayableAbility("one card playable", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Hill Giant", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hill Giant", true);
        checkPlayableAbility("no second card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Silvercoat Lion", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nashi, Moon Sage's Scion", 1);
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertLife(playerA, 20 - 4);
        assertTappedCount("Mountain", true, 0);
        assertExileCount(playerB, "Silvercoat Lion", 1);
    }
}
