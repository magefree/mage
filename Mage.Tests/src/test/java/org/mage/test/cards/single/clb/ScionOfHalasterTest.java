package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

import java.io.FileNotFoundException;

/**
 *
 * @author Rjayz
 */
public class ScionOfHalasterTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderAnafenza_WBG.dck"); // Commander = Anafenza, the Foremost
        return super.createNewGameAndPlayers();
    }

    @Test
    public void TestScionOfHalaster() {
        addCard(Zone.BATTLEFIELD, playerA, "Scion of Halaster", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Cast Commander so Scion of Halaster takes effect
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anafenza, the Foremost");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        skipInitShuffling();

        // Choose to put Island in the graveyard with Scion of Halaster's replacement effect
        setChoice(playerA, "Island");

        // Continue to turn 3 so we get to Player A's draw step
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Before casting Mental Note, the library consists of 2 Islands on top and the Consider on the bottom
        // When casting Mental Note, the two islands will be milled into the graveyard and Consider will be drawn
        assertLibraryCount(playerA, 0);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Mountain", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Island", 1);
    }
}
