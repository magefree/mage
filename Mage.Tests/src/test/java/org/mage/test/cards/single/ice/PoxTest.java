package org.mage.test.cards.single.ice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class PoxTest extends CardTestPlayerBase {

    @Test
    public void test_DiscardAndSacrificeAreSeparateEvents() {
        removeAllCardsFromHand(playerB);

        // Each player loses a third of their life, then discards a third of the cards in their hand, then sacrifices
        // a third of the creatures they control, then sacrifices a third of the lands they control.
        addCard(Zone.HAND, playerA, "Pox"); // {B}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        //
        // Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Titania, Voice of Gaea");

        // discard a land and sacrifice a land are two different events, so two triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pox");
        setChoice(playerA, "Forest"); // discard
        addTarget(playerA, "Grizzly Bears"); // sacrifice creature
        addTarget(playerA, "Swamp"); // sacrifice land
        setChoice(playerA, "Whenever one or more land cards"); // triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 20 - 7 + 2 + 2);
    }
}
