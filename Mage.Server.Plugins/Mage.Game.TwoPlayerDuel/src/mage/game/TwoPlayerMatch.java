
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TwoPlayerMatch extends MatchImpl {

    public TwoPlayerMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        TwoPlayerDuel game = new TwoPlayerDuel(options.getAttackOption(), options.getRange(), mulligan, 20);
        // Sets a start message about the match score
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
