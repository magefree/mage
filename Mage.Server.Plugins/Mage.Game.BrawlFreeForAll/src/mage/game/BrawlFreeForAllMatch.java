package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author spjspj
 */
public class BrawlFreeForAllMatch extends MatchImpl {

    public BrawlFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 30;
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : startLife;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 7;
        BrawlFreeForAll game = new BrawlFreeForAll(
                options.getAttackOption(), options.getRange(),
                mulligan, startLife, startHandSize
        );
        game.setStartMessage(this.createGameStartMessage());
        game.setCheckCommanderDamage(false);
        initGame(game);
        games.add(game);
    }

}
