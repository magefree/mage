package org.mage.test.serverside.base;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.CommanderFreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import java.io.FileNotFoundException;

/**
 * @author LevelX2
 */
public abstract class CardTestCommander3PlayersFFA extends CardTestPlayerAPIImpl {

    public CardTestCommander3PlayersFFA() {
        super();
        this.deckNameA = "CommanderDuel.dck";
        this.deckNameB = "CommanderDuel.dck";
        this.deckNameC = "CommanderDuel.dck";
    }

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new CommanderFreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 40);
        playerA = createPlayer(game, "PlayerA", deckNameA);
        playerB = createPlayer(game, "PlayerB", deckNameB);
        playerC = createPlayer(game, "PlayerC", deckNameC);
        return game;
    }

}
