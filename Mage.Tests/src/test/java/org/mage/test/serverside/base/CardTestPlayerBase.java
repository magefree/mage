package org.mage.test.serverside.base;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import java.io.FileNotFoundException;

/**
 * Base class for testing single cards and effects.
 *
 * @author ayratn
 */
public abstract class CardTestPlayerBase extends CardTestPlayerAPIImpl {

    public CardTestPlayerBase() {
        deckNameA = "RB Aggro.dck";
        deckNameB = "RB Aggro.dck";
    }

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 20);

        playerA = createPlayer(game, "PlayerA", deckNameA);
        playerB = createPlayer(game, "PlayerB", deckNameB);
        return game;
    }

}
