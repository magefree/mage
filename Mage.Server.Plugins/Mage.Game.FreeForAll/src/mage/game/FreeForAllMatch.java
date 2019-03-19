

package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

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
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        FreeForAll game = new FreeForAll(options.getAttackOption(), options.getRange(), mulligan, 20);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
