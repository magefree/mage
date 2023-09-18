package org.mage.test.serverside.base;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.OathbreakerFreeForAll;
import mage.game.mulligan.MulliganType;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

/**
 * @author LevelX2
 */
public abstract class CardTestOathbreaker3PlayersFFA extends CardTestPlayerAPIImpl {

    public CardTestOathbreaker3PlayersFFA() {
        super();
        this.deckNameA = "Oathbreaker_UR.dck"; // PW: Saheeli, Sublime Artificer  SS: Thoughtcast
        this.deckNameB = "Oathbreaker_UR.dck";
        this.deckNameC = "Oathbreaker_UR.dck";
    }

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new OathbreakerFreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        playerA = createPlayer(game, "PlayerA", deckNameA);
        playerB = createPlayer(game, "PlayerB", deckNameB);
        playerC = createPlayer(game, "PlayerC", deckNameC);
        return game;
    }

}