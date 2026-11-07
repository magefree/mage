package org.mage.test.cards.single.yeoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ValMaroonedSurveyorTest extends CardTestPlayerBase {

    @Test
    public void test_Scry() {
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // Scry 1. Draw a card.
        addCard(Zone.HAND, playerA, "Opt"); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opt");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry - keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Surveil() {
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // Surveil 1. Draw a card.
        addCard(Zone.HAND, playerA, "Consider"); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // surveil - keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Investigate() {
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // Draw a card. Investigate.
        addCard(Zone.HAND, playerA, "Deduce"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deduce");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 1);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Discover() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        //
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // When this creature enters, if you cast it, discover 3.
        addCard(Zone.HAND, playerA, "Geological Appraiser"); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geological Appraiser");
        setChoice(playerA, false); // discover - don't cast, put to hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Discover_NothingFound_StillTriggers() {
        // 701.57b A player has "discovered" after the process described in 701.57a is complete,
        // even if some or all of those actions were impossible.
        removeAllCardsFromLibrary(playerA);
        //
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // When this creature enters, if you cast it, discover 3.
        addCard(Zone.HAND, playerA, "Geological Appraiser"); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geological Appraiser");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Seek() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Island");
        //
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // When Hollowhenge Wrangler enters the battlefield, seek a land card.
        addCard(Zone.HAND, playerA, "Hollowhenge Wrangler"); // {3}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hollowhenge Wrangler");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Island", 1);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Seek_NothingFound_NoTrigger() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        //
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // When Hollowhenge Wrangler enters the battlefield, seek a land card.
        addCard(Zone.HAND, playerA, "Hollowhenge Wrangler"); // {3}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hollowhenge Wrangler");

        // no land cards in library - no card was sought
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_OpponentScry_NoTrigger() {
        // Whenever you discover, investigate, scry, surveil, or seek one or more cards, Val deals 2 damage to each
        // opponent and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        //
        // Scry 1. Draw a card.
        addCard(Zone.HAND, playerB, "Opt"); // {U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Opt");
        addTarget(playerB, TestPlayer.TARGET_SKIP); // scry - keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Opt", 0);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
