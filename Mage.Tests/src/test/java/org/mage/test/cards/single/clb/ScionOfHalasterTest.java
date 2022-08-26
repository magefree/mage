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

        // Continue to turn 3 so we get to Player A's draw step
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // AI considers Lightning Bolt a better card than Mountain so it will discard the Mountain
        assertLibraryCount(playerA, 0);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Mountain", 1);
    }

    @Test
    public void TestScionOfHalaster_withPartnerCommanders() {
        addCard(Zone.BATTLEFIELD, playerA, "Scion of Halaster", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Cast commander so Scion of Halaster takes effect
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ishai, Ojutai Dragonspeaker");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        skipInitShuffling();

        // Continue to turn 3 so we get to Player A's draw step
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // AI considers Lightning Bolt a better card than Mountain so it will discard the Mountains
        assertLibraryCount(playerA, 0);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, "Mountain", 2);
    }
}
