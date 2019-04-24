
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

import static mage.game.mulligan.MulliganType.CANADIAN_HIGHLANDER;

/**
 *
 * @author spjspj
 */
public class CanadianHighlanderDuelMatch extends MatchImpl {

    public CanadianHighlanderDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 20;
        Mulligan mulligan = options.getMulliganType().orDefault(CANADIAN_HIGHLANDER).getMulligan(options.getFreeMulligans());
        CanadianHighlanderDuel game = new CanadianHighlanderDuel(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
