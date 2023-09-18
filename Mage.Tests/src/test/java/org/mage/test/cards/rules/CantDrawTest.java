
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantDrawTest extends CardTestPlayerBase {

    /**
     * Leovold, Emissary of Trest does not stop Sylvan Library from drawing
     * extra cards.
     */
    @Test
    public void testCardsNotDrawnFromLibraryEffect() {
        // Each opponent can't draw more than one card each turn.
        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Leovold, Emissary of Trest");

        // At the beginning of your draw step, you may draw two additional cards.
        // If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Library", 1);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 0);

        assertHandCount(playerA, 1); // only one card drawn at start of turn 3
        assertLife(playerA, 16); // pay 4 life for the card dawn normally (no other cards drawn)

    }

    @Test
    public void testCardsNotDrawnFromLibraryEffectNoUse() {
        // Each opponent can't draw more than one card each turn.
        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Leovold, Emissary of Trest");

        // At the beginning of your draw step, you may draw two additional cards.
        // If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Library", 1);

        setChoice(playerA, false);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 0);

        assertHandCount(playerA, 1); // only one card drawn at start of turn 3
        assertLife(playerA, 20); // not used no damage

    }

}
