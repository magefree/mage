package org.mage.test.cards.single.scg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class PrimitiveEtchingsTest extends CardTestPlayerBase {

    @Test
    public void test_FirstCardOnOpponentsTurn() {
        skipInitShuffling();

        // Reveal the first card you draw each turn. Whenever you reveal a creature card this way, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Primitive Etchings");
        //
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // top card

        // each turn, not only your turn
        castSpell(2, PhaseStep.UPKEEP, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 1 + 1); // Think Twice draw + draw for the revealed creature
    }

    @Test
    public void test_OnlyFirstCardEachTurn() {
        skipInitShuffling();

        // Reveal the first card you draw each turn. Whenever you reveal a creature card this way, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Primitive Etchings");
        //
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // second card
        addCard(Zone.LIBRARY, playerA, "Swamp"); // top card

        // turn 3: draw step draws a land first, the creature is the second card of the turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Swamp", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 1 + 1); // draw step + Think Twice
    }
}
