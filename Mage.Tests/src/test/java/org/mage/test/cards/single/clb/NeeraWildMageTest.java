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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Whenever you cast a spell, you may put it on the bottom of its owner's library.
        // If you do, reveal cards from the top of your library until you reveal a nonland card.
        // You may cast that card without paying its mana cost.
        // Then put the rest on the bottom of your library in a random order.
        // This ability triggers only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Neera, Wild Mage", 1);

        addCard(Zone.HAND, playerA, "Consider");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Mental Note");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        // Choose to use Neera's ability
        setChoice(playerA, true);
        // Do not cast Mental Note revealed with Neera's ability
        setChoice(playerA, false);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // Consider was not cast so should be on top of the deck after resolving Neera's ability
        // On turn 3 it will be drawn by player A
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Consider", 1);
        assertLibraryCount(playerA, 3);
        assertLibraryCount(playerA, "Mental Note", 1);
        assertLibraryCount(playerA, "Island", 2);
    }
}
