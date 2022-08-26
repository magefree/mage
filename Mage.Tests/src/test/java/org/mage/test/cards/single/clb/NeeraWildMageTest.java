package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author rjayz
 */
public class NeeraWildMageTest extends CardTestPlayerBase {

    @Test
    public void TestNeeraWildMage() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Whenever you cast a spell, you may put it on the bottom of its owner's library.
        // If you do, reveal cards from the top of your library until you reveal a nonland card.
        // You may cast that card without paying its mana cost.
        // Then put the rest on the bottom of your library in a random order.
        // This ability triggers only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Neera, Wild Mage", 1);

        addCard(Zone.HAND, playerA, "Consider");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Mental Note");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        // Choose to use Neera's ability
        setChoice(playerA, true);
        // Cast Mental Note
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Before casting Mental Note, the library consists of 2 Islands on top and the Consider on the bottom
        // When casting Mental Note, the two islands will be milled into the graveyard and Consider will be drawn
        assertLibraryCount(playerA, 0);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Consider", 1);
        assertGraveyardCount(playerA, 3);
        assertGraveyardCount(playerA, "Mental Note", 1);
        assertGraveyardCount(playerA, "Island", 2);
    }
}
