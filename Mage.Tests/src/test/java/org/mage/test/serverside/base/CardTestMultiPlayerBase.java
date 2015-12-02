package org.mage.test.serverside.base;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

/**
 * Base class for testing single cards and effects in multiplayer game. For PvP
 * games {
 *
 * @see CardTestPlayerBase}
 *
 * @author magenoxx_at_gmail.com
 */
public abstract class CardTestMultiPlayerBase extends CardTestPlayerAPIImpl {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, 0, 20);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

}
