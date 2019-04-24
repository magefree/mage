
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 *
 * @author nigelzor
 */
public class MomirDuelMatch extends MatchImpl {

    public MomirDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        // Momir Vig, Simic Visionary gives +4 starting life
        int startLife = 24;

        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        MomirDuel game = new MomirDuel(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setStartMessage(this.createGameStartMessage());

        this.initGame(game);
        games.add(game);
    }

}
