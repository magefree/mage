package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.player.TestPlayer.TARGET_SKIP;

/**
 * Tests for {@link mage.cards.a.ApproachOfTheSecondSun}
 * <p>
 * Basic functionality tests, bug test, and all Gatherer rulings as of 6/28/2022
 *
 * @author stravant
 * @author the-red-lily
 */
public class ApproachOfTheSecondSunTest extends CardTestPlayerBase {

    @Test
    public void testWinGameTest() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 14);

        // Otherwise, put {this} into its owner's library seventh from the top and you gain 7 life.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", true);

        // If this spell was cast from your hand and you've cast another spell named {this} this game, you win the game.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 27);
        assertLibraryCount(playerA, 1); // 1 approach put back into library
        assertGraveyardCount(playerA, 1); // 1 approach in graveyard (The one that won the game)
        assertHandCount(playerA, 0); // No approaches left in hand
        assertResult(playerA, GameResult.WON);
    }

    @Test
    public void testDontCountOpponentCast() {
        addCard(Zone.HAND, playerA, "Approach of the Second Sun");
        addCard(Zone.HAND, playerB, "Approach of the Second Sun");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 7);

        // First cast of Approach for playerA
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");

        // First cast of Approach for playerB
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Approach of the Second Sun");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.DRAW);
        assertLife(playerA, 27);
        assertLife(playerB, 27);
    }

    @Test
    public void testRightPositionInDeck() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 15);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 1);
        addCard(Zone.HAND, playerA, "Concentrate", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 15);

        // Cast Approach then draw 6
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", true);
        checkLibraryCount("Approach of the Second Sun went to library",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate", true);

        checkLibraryCount("Approach of the Second Sun is not in top 6 cards",
                3, PhaseStep.UPKEEP, playerA, "Approach of the Second Sun", 1);

        // Draw step
        checkLibraryCount("Approach of the Second Sun is 7th card",
                3, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 0);
        checkHandCardCount("Approach of the Second Sun is 7th card",
                3, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 27);
        assertLibraryCount(playerA, 9);
        assertResult(playerA, GameResult.DRAW);
    }

    // 4/18/2017
    // A card that changes zones is considered a new object, so casting the same Approach of the Second Sun card on a
    // later turn is “another spell” named Approach of the Second Sun.
    @Test
    public void testCastSameCard() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 1);
        addCard(Zone.HAND, playerA, "Concentrate", 2);
        addCard(Zone.HAND, playerA, "Equal Treatment", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 24);

        // Cast Approach then draw 7 cards
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate", true); // Draw 3
        checkStackSize("Empty Stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate", true); // Draw 3
        checkStackSize("Empty Stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equal Treatment", true); // Draw 1
        checkStackSize("Empty Stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        // This card changed zones, so it counts as “another spell” named Approach of the Second Sun.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 27);
        assertLibraryCount(playerA, 0);
        assertResult(playerA, GameResult.WON);
    }

    // 4/18/2017
    // If you have fewer than six cards in your library, you'll put Approach of the Second Sun on the bottom of your
    // library. Otherwise, you'll lift up the top six cards without looking at them and place Approach of the Second Sun
    // just under them.
    @Test
    public void testRightPositionInSmallDeck() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 1);
        addCard(Zone.HAND, playerA, "Concentrate", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 15);

        // Cast Approach and draw 3 so all that is left in our library is Approach
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate"); // Draw 3

        // 2 players, so playerA gets draw step on turn 3
        checkLibraryCount("Approach of the Second Sun is not in top 3 cards",
                3, PhaseStep.UPKEEP, playerA, "Approach of the Second Sun", 1);

        // Draw step
        checkLibraryCount("Approach of the Second Sun is 4th card",
                3, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 0);
        checkHandCardCount("Approach of the Second Sun is 4th card",
                3, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 27);
        assertLibraryCount(playerA, 0);
        assertResult(playerA, GameResult.DRAW);
    }

    // 4/18/2017
    // The second Approach of the Second Sun that you cast must be cast from your hand,
    // but first may have been cast from anywhere.
    @Test
    public void testCastFromGraveyard() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 1);
        addCard(Zone.GRAVEYARD, playerA, "Approach of the Second Sun", 2);
        addCard(Zone.HAND, playerA, "Finale of Promise", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mystic Monastery", 25);

        // first may have been cast from anywhere.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Finale of Promise", true);
        // You may cast up to one target instant card and/or up to one target sorcery card from your graveyard each with mana value X or less without paying their mana costs. If a spell cast this way would be put into your graveyard, exile it instead. If X is 10 or more, copy each of those spells twice. You may choose new targets for the copies.
        setChoice(playerA, "X=7"); // each with mana value X or less
        setChoice(playerA, "Yes"); // You may cast
        addTarget(playerA, TARGET_SKIP); // up to one target instant card
        addTarget(playerA, "Approach of the Second Sun"); // and/or up to one target sorcery card from your graveyard
        checkLife("Approach of the Second Sun cast from graveyard gains life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 27);
        checkLibraryCount("Approach of the Second Sun cast from graveyard goes to library",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 1);

        // This casting should not win the game, just gain life
        // The second Approach of the Second Sun that you cast must be cast from your hand,
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Finale of Promise", true);
        setChoice(playerA, "X=7"); // each with mana value X or less
        setChoice(playerA, "Yes"); // You may cast
        addTarget(playerA, TARGET_SKIP); // up to one target instant card
        addTarget(playerA, "Approach of the Second Sun"); // and/or up to one target sorcery card from your graveyard
        checkLife("Approach of the Second Sun cast from graveyard gains life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 34);
        checkLibraryCount("Approach of the Second Sun cast from graveyard goes to library",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 2);

        // This casting should win the game and not gain life
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Approach of the Second Sun");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.WON);
        assertLife(playerA, 34);
        assertLibraryCount(playerA, "Approach of the Second Sun", 2);
    }

    // Copy CARD not copy SPELL (copied cards are cast)
    @Test
    public void testCastCopyCard() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 1);
        addCard(Zone.GRAVEYARD, playerA, "Approach of the Second Sun", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mystic Monastery", 25);
        addCard(Zone.BATTLEFIELD, playerA, "Demilich");

        // Copied CARD counts as first Approach cast
        attack(1, playerA, "Demilich");
        // Whenever Demilich attacks, exile up to one target instant or sorcery card from your graveyard. Copy it. You may cast the copy.
        addTarget(playerA, "Approach of the Second Sun"); // exile up to one target instant or sorcery card from your graveyard. Copy it.
        setChoice(playerA, "Yes"); // You may cast the copy.
        checkLife("Approach of the Second Sun cast from graveyard gains life", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 27);
        checkLibraryCount("Copied Card Approach of the Second Sun cast from graveyard does not go to library",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 0);

        // Copied CARD does not count as second Approach cast
        attack(3, playerA, "Demilich");
        // Whenever Demilich attacks, exile up to one target instant or sorcery card from your graveyard. Copy it. You may cast the copy.
        addTarget(playerA, "Approach of the Second Sun"); // exile up to one target instant or sorcery card from your graveyard. Copy it.
        setChoice(playerA, "Yes"); // You may cast the copy.
        checkLife("Approach of the Second Sun cast from graveyard gains life", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 27);
        checkLibraryCount("Copied Card Approach of the Second Sun cast from graveyard does not go to library",
                3, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 0);

        // This casting should win the game and not gain life
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Approach of the Second Sun");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.WON);
        assertLife(playerA, 34);
        assertLibraryCount(playerA, "Approach of the Second Sun", 0);
    }

    // 4/18/2017
    // A copy of a spell isn't cast, so it won't count as the first nor as the second Approach of the Second Sun.
    // Also test for bug https://github.com/magefree/mage/issues/9126
    @Test
    public void testCastCopy() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 2);
        addCard(Zone.HAND, playerA, "Fork", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mystic Monastery", 18);

        // First copy doesn't cause first cast of Approach to win the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fork", "Approach of the Second Sun", "Cast Approach of the Second Sun"); // Copy Approach of the Second Sun
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 2); // Wait for Fork to copy and for copy to resolve
        checkStackSize("Approach of the Second Sun on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkLife("Copy of Approach of the Second Sun gains life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 27);
        checkLibraryCount("Copy of Approach of the Second Sun does not put a card in library",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 0);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("Approach of the Second Sun gains life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 34);
        checkLibraryCount("Approach of the Second Sun goes to library",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 1);

        // Second copy doesn't win the game
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fork", "Approach of the Second Sun", "Cast Approach of the Second Sun"); // Copy Approach of the Second Sun
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, 2); // Wait for Fork to copy and for copy to resolve
        checkLife("Copy of 2nd Approach of the Second Sun gains life", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 41);
        checkLibraryCount("Copy of 2nd Approach of the Second Sun does not put a card in library",
                1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Approach of the Second Sun", 1); //Already have 1 in library

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.WON);
        assertLife(playerA, 41);
        assertLibraryCount(playerA, "Approach of the Second Sun", 1);
    }

    // 4/18/2017
    // As your second Approach of the Second Sun resolves, it checks only whether the first one was cast,
    // not whether the first one resolved. If your first Approach of the Second Sun was countered,
    // you'll still win the game as your second one resolves.
    @Test
    public void testFirstCountered() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 2);
        addCard(Zone.HAND, playerA, "Counterspell", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 16);

        // If your first Approach of the Second Sun was countered, you'll still win the game as your second one resolves.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Approach of the Second Sun", "Cast Approach of the Second Sun");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20); // Countered, no lifegain
        assertLibraryCount(playerA, "Approach of the Second Sun", 0); // 0 approach put back into library
        assertGraveyardCount(playerA, "Approach of the Second Sun", 2); // 2 approach in graveyard (The one that won the game and the one that was countered)
        assertHandCount(playerA, 0); // No approaches left in hand
        assertResult(playerA, GameResult.WON);
    }

    // 7/14/2017
    // Approach of the Second Sun has no effect until it's resolving.
    // If the second one you cast is countered, you won't win the game.
    @Test
    public void testSecondCountered() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 2);
        addCard(Zone.HAND, playerA, "Counterspell", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 16);

        // If the second one you cast is countered, you won't win the game.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Approach of the Second Sun", "Cast Approach of the Second Sun");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 27); // lifegain from first spell
        assertLibraryCount(playerA, "Approach of the Second Sun", 1); // 1 approach put back into library
        assertGraveyardCount(playerA, "Approach of the Second Sun", 1); // 1 approach in graveyard (The one that was countered)
        assertHandCount(playerA, 0); // No approaches left in hand
        assertResult(playerA, GameResult.DRAW);
    }
}
