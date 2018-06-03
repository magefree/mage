
package org.mage.test.serverside.base;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.CommanderFreeForAll;
import mage.game.Game;
import mage.game.GameException;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

/**
 *
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
        Game game = new CommanderFreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, 0, 40);
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        return game;
    }

}
