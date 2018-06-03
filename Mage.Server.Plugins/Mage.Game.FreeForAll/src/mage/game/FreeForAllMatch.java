

package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FreeForAllMatch extends MatchImpl {

    public FreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        FreeForAll game = new FreeForAll(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), 20);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
