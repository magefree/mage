package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SanarInnovativeFirstYearTest extends CardTestPlayerBase {

    @Test
    public void testVividCannotExileCardsOutsideControlledPermanentColors() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Sanar, Innovative First-Year");
        addCard(Zone.LIBRARY, playerA, "Healing Salve"); // white
        addCard(Zone.LIBRARY, playerA, "Giant Growth"); // green

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 0);
        assertLibraryCount(playerA, "Healing Salve", 1);
        assertLibraryCount(playerA, "Giant Growth", 1);
    }

    @Test
    public void testVividExilesOnlyMatchingColorAmongRevealedCards() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Sanar, Innovative First-Year");
        addCard(Zone.LIBRARY, playerA, "Healing Salve"); // white
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // red

        setChoice(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertLibraryCount(playerA, "Healing Salve", 1);
    }
}
