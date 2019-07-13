package org.mage.test.cards.single.akh;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * @author stravant
 */
public class RagsRichesTest extends CardTestMultiPlayerBase {
    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    @Test
    public void testRiches() {
        addCard(Zone.GRAVEYARD, playerA, "Rags // Riches");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Squire");
        addCard(Zone.BATTLEFIELD, playerC, "Invisible Stalker"); // Make sure that there aren't targeting restrictions
        addCard(Zone.HAND, playerD, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Riches");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 1);
        assertPermanentCount(playerB, 0);
        assertPermanentCount(playerC, 0);
        assertPermanentCount(playerA, 9);
    }
}
