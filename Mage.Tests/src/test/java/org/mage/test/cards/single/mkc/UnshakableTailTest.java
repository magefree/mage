package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class UnshakableTailTest extends CardTestPlayerBase {

    @Test
    public void test_CreatureCardsMilled_OneTrigger() {
        removeAllCardsFromLibrary(playerA);

        // When Unshakable Tail enters the battlefield and at the beginning of your upkeep, surveil 1.
        // Whenever one or more creature cards are put into your graveyard from your library, investigate.
        addCard(Zone.BATTLEFIELD, playerA, "Unshakable Tail");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // upkeep surveil: keep on top
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 2);
        addCard(Zone.LIBRARY, playerA, "Forest", 1);

        // mill 2 creatures and 1 land at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerA, "Clue Token", 1);
    }

    @Test
    public void test_CreatureCardsFromHand_NoTrigger() {
        // When Unshakable Tail enters the battlefield and at the beginning of your upkeep, surveil 1.
        // Whenever one or more creature cards are put into your graveyard from your library, investigate.
        addCard(Zone.BATTLEFIELD, playerA, "Unshakable Tail");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // upkeep surveil: keep on top
        //
        // Target player discards two cards.
        addCard(Zone.HAND, playerA, "Mind Rot"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Rot", playerA); // discard all hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerA, "Clue Token", 0);
    }
}
