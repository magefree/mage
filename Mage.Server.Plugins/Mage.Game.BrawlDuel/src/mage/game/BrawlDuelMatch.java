package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author spjspj
 */
public class BrawlDuelMatch extends MatchImpl {

    public BrawlDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 25;
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        BrawlDuel game = new BrawlDuel(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setCheckCommanderDamage(false);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }
}
