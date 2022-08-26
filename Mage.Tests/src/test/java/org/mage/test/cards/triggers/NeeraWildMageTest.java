package org.mage.test.cards.triggers;

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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Whenever you cast a spell, you may put it on the bottom of its owner's library.
        // If you do, reveal cards from the top of your library until you reveal a nonland card.
        // You may cast that card without paying its mana cost.
        // Then put the rest on the bottom of your library in a random order.
        // This ability triggers only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Neera, Wild Mage", 1);

        addCard(Zone.HAND, playerA, "Consider");

        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Mental Note");
        addCard(Zone.LIBRARY, playerA, "Island");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        // Put Ponder on bottom of library
        setChoice(playerA, true);
        // Cast Mental Note
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Assert Consider was put on the bottom of the library
        assertLibraryCount(playerA, "Consider", 1);
        // Assert the Island on top of Mental note was put on the bottom of the library
        assertLibraryCount(playerA, "Island", 1);
        // Assert Mental Note was cast
        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, "Mental Note", 1);
        assertGraveyardCount(playerA, "Island", 2);
    }
}
