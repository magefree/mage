package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author JayDi85
 */
public class OathbreakerFreeForAllMatch extends MatchImpl {

    public OathbreakerFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        int startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : 20;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 7;
        OathbreakerFreeForAll game = new OathbreakerFreeForAll(
                options.getAttackOption(), options.getRange(),
                mulligan, startLife, startHandSize
        );
        game.setCheckCommanderDamage(false);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
