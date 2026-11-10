package org.mage.test.cards.single.clu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class DimirStrandcatcherMultiplayerTest extends CardTestCommander4Players {

    @Test
    public void test_AttackTwoOfThreeOpponents_SurveilTwo() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        // library, last added on top
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Swamp"); // draw on turn 1

        // Whenever you attack, surveil X, where X is the number of opponents being attacked.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Strandcatcher"); // 3/3, flying
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        attack(1, playerA, "Dimir Strandcatcher", playerB);
        attack(1, playerA, "Grizzly Bears", playerC);
        // surveil 2 - look at Island and Forest only
        addTarget(playerA, TestPlayer.TARGET_SKIP); // keep both on top
        setChoice(playerA, "Forest"); // order on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertLife(playerC, 20 - 2);
        assertLife(playerD, 20);
        assertHandCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, 0);
        assertLibraryCount(playerA, 3);
    }
}
