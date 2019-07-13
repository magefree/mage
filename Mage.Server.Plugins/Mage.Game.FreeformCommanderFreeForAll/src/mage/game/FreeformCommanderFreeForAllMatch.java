package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author spjspj
 */
public class FreeformCommanderFreeForAllMatch extends MatchImpl {

    public FreeformCommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        FreeformCommanderFreeForAll game = new FreeformCommanderFreeForAll(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
