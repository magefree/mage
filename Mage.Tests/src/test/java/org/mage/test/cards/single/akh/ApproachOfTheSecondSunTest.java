package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author stravant
 */
public class ApproachOfTheSecondSunTest extends CardTestPlayerBase {
    @Test
    public void testWinGametest() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 14);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 27);
        assertLibraryCount(playerA, 1); // 1 approach in graveyard (The one that won the game)
        assertGraveyardCount(playerA, 1); // 1 approach put back into library
        assertHandCount(playerA, 0); // No aproaches left in hand
        assertResult(playerA, GameResult.WON);
    }

    @Test
    public void testDontCountOpponentCast() {
        addCard(Zone.HAND, playerA, "Approach of the Second Sun");
        addCard(Zone.HAND, playerB, "Approach of the Second Sun");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 7);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Approach of the Second Sun");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertResult(playerA, GameResult.DRAW);
        assertLife(playerA, 27);
        assertLife(playerB, 27);
    }

    @Test
    public void testRightPositionInDeck() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 1);
        addCard(Zone.HAND, playerA, "Concentrate", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 15);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 27);
        assertLibraryCount(playerA, "Approach of the Second Sun", 1); // 1 approach put back into library in the right place
        assertLibraryCount(playerA, 1);
        assertResult(playerA, GameResult.DRAW);
    }
}
