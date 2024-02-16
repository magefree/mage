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
        setDecknamePlayerA("CommanderDuel_Partners.dck"); // Commander = Ishai, Ojutai Dragonspeaker and Thrasios, Triton Hero
        return super.createNewGameAndPlayers();
    }

    @Test
    public void TestScionOfHalaster() {
        addCard(Zone.BATTLEFIELD, playerA, "Scion of Halaster", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Cast commander so Scion of Halaster takes effect
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        skipInitShuffling();

        setStrictChooseMode(true);

        // Choose to put Mountain in the graveyard with Scion of Halaster's effect on Thrasios, Triton Hero
        addTarget(playerA, "Mountain");

        // Continue to turn 3 so we get to Player A's draw step
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Lightning Bolt in library stays on top and will be drawn
        assertLibraryCount(playerA, 0);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Mountain", 1);
    }

    /**
     * Test if Scion of Halaster's effect correctly replaces itself when there are multiple commander creatures who have it.
     * For example, when a commander deck has two partner commanders and both are on the battlefield.
     */
    @Test
    public void TestScionOfHalaster_withPartnerCommanders() {
        addCard(Zone.BATTLEFIELD, playerA, "Scion of Halaster", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Cast both partner commanders, Scion of Halaster grants its ability to both of them
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ishai, Ojutai Dragonspeaker");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        skipInitShuffling();

        setStrictChooseMode(true);

        // Choose for Thrasios, Triton Hero's replacement effect granted by Scion of Halaster to apply first
        setChoice(playerA, "Thrasios, Triton Hero");

        // Choose to put Mountain in the graveyard with Scion of Halaster's effect on Thrasios, Triton Hero
        addTarget(playerA, "Mountain");

        // Choose to put Island in the graveyard with Scion of Halaster's effect on Ishai, Ojutai Dragonspeaker
        addTarget(playerA, "Island");

        // Continue to turn 3 so we get to Player A's draw step
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Lightning Bolt in library stays on top and will be drawn
        assertLibraryCount(playerA, 0);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertGraveyardCount(playerA, "Island", 1);
    }
}
