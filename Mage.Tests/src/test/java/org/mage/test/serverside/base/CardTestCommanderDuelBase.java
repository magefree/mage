
package org.mage.test.serverside.base;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.CommanderDuel;
import mage.game.Game;
import mage.game.GameException;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

/**
 *
 * @author LevelX2
 */
public abstract class CardTestCommanderDuelBase extends CardTestPlayerAPIImpl {

    public CardTestCommanderDuelBase() {
        super();
        this.deckNameA = "CommanderDuel.dck"; // Commander Ob Nixilis of the Black Oath
        this.deckNameB = "CommanderDuel.dck"; // Commander Ob Nixilis of the Black Oath
    }

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new CommanderDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, 0, 40);

        playerA = createPlayer(game, playerA, "PlayerA", deckNameA);
        playerB = createPlayer(game, playerB, "PlayerB", deckNameB);
        return game;
    }

}
