package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GeometersArthropodTest extends CardTestPlayerBase {

    private static final String arthropod = "Geometer's Arthropod";

    /**
     * Geometer's Arthropod should trigger when its controller casts a spell with {X} in its mana cost.
     *
     * This covers the positive X path:
     * - Cast Blaze with X=2.
     * - Trigger Geometer's Arthropod from the spell cast.
     * - Look at the top two library cards, choose one for hand, and put the other on the bottom.
     * - Let Blaze still resolve normally for X damage.
     */
    @Test
    public void testXSpellLooksAtTopXAndPutsOneIntoHand() {
        setStrictChooseMode(true);

        // Setup: Arthropod is on the battlefield, Blaze is the X spell, and the top library cards are controlled.
        addCard(Zone.BATTLEFIELD, playerA, arthropod);
        addCard(Zone.HAND, playerA, "Blaze");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Memnite");
        skipInitShuffling();

        // Action: cast an X spell with X=2; this should make Arthropod look at exactly two cards.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=2");

        // Trigger choice: pick one of the two seen cards to put into hand.
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Final state: the chosen card moved to hand, the other looked-at card stayed in library, and Blaze dealt 2.
        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, "Silvercoat Lion", 1);
        assertLibraryCount(playerA, "Memnite", 1);
        assertLife(playerB, 18);
    }

    /**
     * Geometer's Arthropod should still trigger for an X spell with X=0, but it should move no cards.
     *
     * This protects the zero edge case:
     * - Cast Blaze with X=0.
     * - Resolve the Arthropod trigger without a target prompt or card movement.
     * - Let Blaze resolve for zero damage.
     */
    @Test
    public void testXZeroDoesNotMoveCards() {
        setStrictChooseMode(true);

        // Setup: one known card in library makes accidental movement easy to detect.
        addCard(Zone.BATTLEFIELD, playerA, arthropod);
        addCard(Zone.HAND, playerA, "Blaze");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        skipInitShuffling();

        // Action: cast the X spell with X=0. The Arthropod trigger should resolve harmlessly.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=0");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Final state: no card was put into hand or removed from the library, and no damage was dealt.
        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertLibraryCount(playerA, "Silvercoat Lion", 1);
        assertLife(playerB, 20);
    }
}
